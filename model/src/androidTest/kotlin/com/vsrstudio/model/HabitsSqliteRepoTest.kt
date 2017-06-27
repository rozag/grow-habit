package com.vsrstudio.model

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.test.InstrumentationRegistry
import com.vsrstudio.entity.domain.*
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.CompletionEntry
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.DatabaseInfo
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.HabitEntry
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.Table
import com.vsrstudio.model.mapper.CompletionFromCursorMapper
import com.vsrstudio.model.mapper.CompletionToContentValuesMapper
import com.vsrstudio.model.mapper.HabitFromCursorMapper
import com.vsrstudio.model.mapper.HabitToContentValuesMapper
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
        assertEquals(listOf(habitToAdd), queryAllHabits())
    }

    @Test
    fun addMultipleHabits_habitsAdded() {
        val habitsList = generateHabitsList()
        repo.add(habitsList)
        assertEquals(habitsList, queryAllHabits())
    }

    @Test
    fun updateSingleHabit_habitUpdated() {
        val oldIndex = 0
        val habit = generateHabit(oldIndex)
        addHabits(listOf(habit))
        val updatedHabit = generateUpdatedHabit(habit, oldIndex)
        repo.update(updatedHabit)
        assertEquals(listOf(updatedHabit), queryAllHabits())
    }

    @Test
    fun updateMultipleHabits_habitsUpdated() {
        val habits = generateHabitsList()
        addHabits(habits)
        val updatedHabits = generateUpdatedHabitsList(habits)
        repo.update(updatedHabits)
        assertEquals(updatedHabits, queryAllHabits())
    }

    @Test
    fun removeSingleHabit_habitRemoved() {
    }

    @Test
    fun queryAllHabits_allHabitsReturned() {
    }

    private fun applyToWritableDb(func: (SQLiteDatabase) -> Unit) {
        val writableDb = dbOpenHelper.writableDatabase
        writableDb.beginTransaction()
        func(writableDb)
        writableDb.setTransactionSuccessful()
        writableDb.endTransaction()
    }

    private fun addHabits(habits: List<Habit>) = applyToWritableDb { writableDatabase ->
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

    private fun queryAllHabits(): List<Habit> {
        val readableDb = dbOpenHelper.readableDatabase
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

}