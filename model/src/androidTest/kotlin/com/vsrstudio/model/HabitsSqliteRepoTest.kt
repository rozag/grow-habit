package com.vsrstudio.model

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.test.InstrumentationRegistry
import com.vsrstudio.arch.Query
import com.vsrstudio.entity.domain.*
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.CompletionEntry
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.DatabaseInfo
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.HabitEntry
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.Table
import com.vsrstudio.model.mapper.CompletionFromCursorMapper
import com.vsrstudio.model.mapper.CompletionToContentValuesMapper
import com.vsrstudio.model.mapper.HabitFromCursorMapper
import com.vsrstudio.model.mapper.HabitToContentValuesMapper
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class HabitsSqliteRepoTest {

    private val completionToContentValuesMapper = CompletionToContentValuesMapper()
    private val completionFromCursorMapper = CompletionFromCursorMapper()
    private val habitToContentValuesMapper = HabitToContentValuesMapper()
    private val habitFromCursorMapper = HabitFromCursorMapper()
    private lateinit var context: Context
    private lateinit var dbOpenHelper: HabitsSqliteOpenHelper
    private lateinit var repo: HabitsSqliteRepo

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getTargetContext()
        context.deleteDatabase(DatabaseInfo.name)
        dbOpenHelper = HabitsSqliteOpenHelper(context)
        repo = HabitsSqliteRepo(
                dbOpenHelper,
                habitToContentValuesMapper,
                habitFromCursorMapper,
                completionToContentValuesMapper,
                completionFromCursorMapper
        )
    }

    @After
    fun tearDown() {
        dbOpenHelper.close()
        context.deleteDatabase(DatabaseInfo.name)
    }

    @Test
    fun addSingleHabit_habitAdded() {
        val habitToAdd = generateHabit()
        repo.add(habitToAdd)
        assertEquals(listOf(habitToAdd), queryAllHabits(dbOpenHelper.readableDatabase))
    }

    @Test
    fun addMultipleHabits_habitsAdded() {
        val habitsList = generateHabitsList()
        repo.add(habitsList)
        assertEquals(habitsList, queryAllHabits(dbOpenHelper.readableDatabase))
    }

    @Test
    fun updateSingleHabit_habitUpdated() {
        val oldIndex = 0
        val habit = generateHabit(oldIndex)
        addHabits(dbOpenHelper.writableDatabase, listOf(habit))
        val updatedHabit = generateUpdatedHabit(habit, oldIndex)
        repo.update(updatedHabit)
        assertEquals(listOf(updatedHabit), queryAllHabits(dbOpenHelper.readableDatabase))
    }

    @Test
    fun updateMultipleHabits_habitsUpdated() {
        val habits = generateHabitsList()
        addHabits(dbOpenHelper.writableDatabase, habits)
        val updatedHabits = generateUpdatedHabitsList(habits)
        repo.update(updatedHabits)
        assertEquals(updatedHabits, queryAllHabits(dbOpenHelper.readableDatabase))
    }

    @Test
    fun removeSingleHabit_habitRemoved() {
        val habit = generateHabit()
        addHabits(dbOpenHelper.writableDatabase, listOf(habit))
        repo.remove(habit)
        assertEquals(listOf<Habit>(), queryAllHabits(dbOpenHelper.readableDatabase))
    }

    @Test
    fun queryAllHabits_allHabitsReturned() {
        val habits = generateHabitsList()
        addHabits(dbOpenHelper.writableDatabase, habits)
        val allHabitsQuery = object : Query<Habit, SQLiteDatabase> {
            override fun query(readableStorage: SQLiteDatabase): List<Habit> {
                return queryAllHabits(dbOpenHelper.writableDatabase)
            }

            override fun uniqueId(): String = "test_query_id"
        }
        repo.query(allHabitsQuery)
                .test()
                .assertValue(habits)
    }

    @Test
    fun addSingleHabit_observersNotified() {
        val habitToAdd = generateHabit()
        val habitsList = listOf(habitToAdd)
        val observers = subscribeDifferentObservers(repo, habitsList)
        repo.add(habitToAdd)
        assertObserversValues(observers, habitsList)
    }

    @Test
    fun addMultipleHabits_observersNotified() {
        val habitsList = generateHabitsList()
        val observers = subscribeDifferentObservers(repo, habitsList)
        repo.add(habitsList)
        assertObserversValues(observers, habitsList)
    }

//    @Test
//    fun updateSingleHabit_observersNotified() {
//        val oldIndex = 0
//        val habit = generateHabit(oldIndex)
//        addHabits(listOf(habit))
//        val updatedHabit = generateUpdatedHabit(habit, oldIndex)
//        repo.update(updatedHabit)
//        // TODO:
//    }

//    @Test
//    fun updateMultipleHabits_observersNotified() {
//        val habits = generateHabitsList()
//        addHabits(habits)
//        val updatedHabits = generateUpdatedHabitsList(habits)
//        repo.update(updatedHabits)
//        // TODO:
//    }

//    @Test
//    fun removeSingleHabit_observersNotified() {
//        val habit = generateHabit()
//        addHabits(listOf(habit))
//        repo.remove(habit)
//        // TODO:
//    }

    private fun applyToWritableDb(writableDb: SQLiteDatabase, func: (SQLiteDatabase) -> Unit) {
        writableDb.beginTransaction()
        func(writableDb)
        writableDb.setTransactionSuccessful()
        writableDb.endTransaction()
    }

    private fun addHabits(writableDb: SQLiteDatabase, habits: List<Habit>) {
        applyToWritableDb(writableDb) { writableDatabase ->
            val habitsContentValues = habitToContentValuesMapper.batchMap(habits)
            val completionsContentValues = habits.flatMap { habit ->
                completionToContentValuesMapper.batchMap(habit.completions)
            }
            habitsContentValues.forEach { cv ->
                writableDatabase.insert(Table.habit, null, cv)
            }
            completionsContentValues.forEach { cv ->
                writableDatabase.insert(Table.completion, null, cv)
            }
        }
    }

    private fun queryAllHabits(readableDb: SQLiteDatabase): List<Habit> {
        val habitsCursor = readableDb.rawQuery(
                "SELECT * FROM ${Table.habit};",
                null
        )
        val habitsIdsSelection = buildHabitsIdsSelectionString(habitsCursor)
        val completionsCursor = readableDb.rawQuery(
                "SELECT * " +
                        "FROM ${Table.completion} " +
                        "WHERE ${CompletionEntry.habitId} IN ($habitsIdsSelection);",
                null
        )
        val completions = completionFromCursorMapper.batchMap(completionsCursor)
        completionsCursor.close()
        val habitIdToCompletionListMap = buildHabitIdToCompletionsListMapping(completions)
        val habits = habitFromCursorMapper.batchMap(habitsCursor, habitIdToCompletionListMap)
        habitsCursor.close()
        return habits
    }

    private fun buildHabitsIdsSelectionString(habitsCursor: Cursor): String {
        val habitsIdsList = ArrayList<String>(habitsCursor.count)
        val idColInd = habitsCursor.getColumnIndex(HabitEntry.id)
        habitsCursor.moveToFirst()
        val habitsIdsSelection: String
        if (habitsCursor.count > 0) {
            do {
                habitsIdsList.add(habitsCursor.getString(idColInd))
            } while (habitsCursor.moveToNext())
            val sb = StringBuilder()
            habitsIdsList.forEachIndexed { index, id ->
                sb.append("'$id'")
                if (index != habitsIdsList.lastIndex) {
                    sb.append(", ")
                }
            }
            habitsIdsSelection = sb.toString()
        } else {
            habitsIdsSelection = ""
        }
        return habitsIdsSelection
    }

    private fun buildHabitIdToCompletionsListMapping(completions: List<Completion>):
            MutableMap<String, MutableList<Completion>> {
        val habitIdToCompletionListMap = mutableMapOf<String, MutableList<Completion>>()
        completions.forEach { completion ->
            val habitId = completion.habitId.value
            val nullableListFromMap: MutableList<Completion>? = habitIdToCompletionListMap[habitId]
            val listFromMap: MutableList<Completion>
            if (nullableListFromMap == null) {
                listFromMap = ArrayList<Completion>()
                habitIdToCompletionListMap[habitId] = listFromMap
            } else {
                listFromMap = nullableListFromMap
            }
            listFromMap.add(completion)
        }
        return habitIdToCompletionListMap
    }

    private fun generateHabitId(index: Int): Id = Id("habit_id_$index")

    private fun generateHabitTitle(index: Int): Title = Title("habit_title_$index")

    private fun generateCompletionId(index: Int, postfix: Int): Id = Id("completion_id_${postfix}_$index")

    private fun randomLongFromInterval(begin: Long, end: Long): Long {
        val range = end - begin + 1
        return (Math.random() * range).toLong() + begin
    }

    private fun generateCompletion(id: Id, habitId: Id): Completion {
        return Completion(
                id,
                habitId,
                Completion.Status.fromInt(randomLongFromInterval(0, 3).toInt()),
                Date(randomLongFromInterval(1, 1000))
        )
    }

    private fun generateCompletionsList(habitId: Id, index: Int, count: Int = 3): List<Completion> {
        return (0..count - 1)
                .map { i -> generateCompletionId(i, index) }
                .map { completionId -> generateCompletion(completionId, habitId) }
    }

    private fun generateHabit(index: Int = 0): Habit {
        val habitId = generateHabitId(index)
        val habitTitle = generateHabitTitle(index)
        val completions = generateCompletionsList(habitId, index)
        return Habit(habitId, habitTitle, completions, index)
    }

    private fun generateHabitsList(count: Int = 3): List<Habit> {
        return (0..count - 1)
                .map { index -> generateHabit(index) }
    }

    private fun generateUpdatedHabit(habit: Habit, newIndex: Int): Habit {
        val newTitle = generateHabitTitle(newIndex)
        val newPosition = habit.position + 1
        val newCompletionId = generateCompletionId(habit.completions.size, newIndex)
        val newCompletion = generateCompletion(newCompletionId, habit.id)
        return habit.rename(newTitle)
                .addCompletion(newCompletion)
                .changePosition(newPosition)
    }

    private fun generateUpdatedHabitsList(habits: List<Habit>): List<Habit> {
        return habits.mapIndexed { index, habit -> generateUpdatedHabit(habit, habits.size + index) }
    }

    private fun generateQueryForSingleHabit(id: Id): Query<Habit, SQLiteDatabase> {
        return object : Query<Habit, SQLiteDatabase> {
            override fun query(readableStorage: SQLiteDatabase): List<Habit> {
                return queryAllHabits(readableStorage)
                        .filter { (habitId) -> habitId == id }
            }

            override fun uniqueId(): String = "query_single_habits"
        }
    }

    private fun generateQueryForSeveralHabits(ids: List<Id>): Query<Habit, SQLiteDatabase> {
        return object : Query<Habit, SQLiteDatabase> {
            override fun query(readableStorage: SQLiteDatabase): List<Habit> {
                return queryAllHabits(readableStorage)
                        .filter { (habitId) -> habitId in ids }
            }

            override fun uniqueId(): String = "query_several_habits"
        }
    }

    private fun generateQueryForAllHabits(): Query<Habit, SQLiteDatabase> {
        return object : Query<Habit, SQLiteDatabase> {
            override fun query(readableStorage: SQLiteDatabase): List<Habit> {
                return queryAllHabits(readableStorage)
            }

            override fun uniqueId(): String = "query_all_habits"
        }
    }

    private fun subscribeDifferentObservers(repo: HabitsSqliteRepo,
                                            habits: List<Habit>): List<TestObserver<List<Habit>>> {
        return listOf(
                repo.query(generateQueryForSingleHabit(habits[0].id))
                        .skip(1) // Skip the first emission
                        .test(),
                repo.query(generateQueryForSeveralHabits(
                        habits.filterIndexed { index, _ -> index % 2 == 0 }.map { (id) -> id }))
                        .skip(1) // Skip the first emission
                        .test(),
                repo.query(generateQueryForAllHabits())
                        .skip(1) // Skip the first emission
                        .test()
        )
    }

    private fun assertObserversValues(observers: List<TestObserver<List<Habit>>>, habits: List<Habit>) {
        observers[0].assertValue(habits.subList(0, 1))
        observers[1].assertValue(habits.filterIndexed { index, _ -> index % 2 == 0 })
        observers[2].assertValue(habits)
    }

}