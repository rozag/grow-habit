package com.vsrstudio.model

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.vsrstudio.arch.Query
import com.vsrstudio.entity.domain.*
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.CompletionEntry
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.HabitEntry
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.Table
import com.vsrstudio.model.mapper.CompletionFromCursorMapper
import com.vsrstudio.model.mapper.CompletionToContentValuesMapper
import com.vsrstudio.model.mapper.HabitFromCursorMapper
import com.vsrstudio.model.mapper.HabitToContentValuesMapper
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals

fun applyToWritableDb(writableDb: SQLiteDatabase, func: (SQLiteDatabase) -> Unit) {
    writableDb.beginTransaction()
    func(writableDb)
    writableDb.setTransactionSuccessful()
    writableDb.endTransaction()
}

fun addHabits(writableDb: SQLiteDatabase,
              habits: List<Habit>,
              habitToContentValuesMapper: HabitToContentValuesMapper,
              completionToContentValuesMapper: CompletionToContentValuesMapper) {
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

fun queryAllHabits(readableDb: SQLiteDatabase,
                   completionFromCursorMapper: CompletionFromCursorMapper,
                   habitFromCursorMapper: HabitFromCursorMapper): List<Habit> {
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

fun buildHabitsIdsSelectionString(habitsCursor: Cursor): String {
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

fun buildHabitIdToCompletionsListMapping(completions: List<Completion>):
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

fun generateHabitId(index: Int): Id = Id("habit_id_$index")

fun generateHabitTitle(index: Int): Title = Title("habit_title_$index")

fun generateCompletionId(index: Int, postfix: Int): Id = Id("completion_id_${postfix}_$index")

fun randomLongFromInterval(begin: Long, end: Long): Long {
    val range = end - begin + 1
    return (Math.random() * range).toLong() + begin
}

fun generateCompletion(id: Id, habitId: Id): Completion {
    return Completion(
            id,
            habitId,
            Completion.Status.fromInt(randomLongFromInterval(0, 3).toInt()),
            Date(randomLongFromInterval(1, 1000))
    )
}

fun generateCompletionsList(habitId: Id, index: Int, count: Int = 3): List<Completion> {
    return (0..count - 1)
            .map { i -> generateCompletionId(i, index) }
            .map { completionId -> generateCompletion(completionId, habitId) }
}

fun generateHabit(index: Int = 0): Habit {
    val habitId = generateHabitId(index)
    val habitTitle = generateHabitTitle(index)
    val completions = generateCompletionsList(habitId, index)
    return Habit(habitId, habitTitle, completions, index)
}

fun generateHabitsList(count: Int = 3): List<Habit> {
    return (0..count - 1)
            .map { index -> generateHabit(index) }
}

fun generateUpdatedHabit(habit: Habit, newIndex: Int): Habit {
    val newTitle = generateHabitTitle(newIndex)
    val newPosition = habit.position + 1
    val newCompletionId = generateCompletionId(habit.completions.size, newIndex)
    val newCompletion = generateCompletion(newCompletionId, habit.id)
    return habit.rename(newTitle)
            .addCompletion(newCompletion)
            .changePosition(newPosition)
}

fun generateUpdatedHabitsList(habits: List<Habit>): List<Habit> {
    return habits.mapIndexed { index, habit -> generateUpdatedHabit(habit, habits.size + index) }
}

fun generateQueryForSingleHabit(id: Id,
                                completionFromCursorMapper: CompletionFromCursorMapper,
                                habitFromCursorMapper: HabitFromCursorMapper): Query<Habit, SQLiteDatabase> {
    return object : Query<Habit, SQLiteDatabase> {
        override fun query(readableStorage: SQLiteDatabase): List<Habit> {
            return queryAllHabits(readableStorage, completionFromCursorMapper, habitFromCursorMapper)
                    .filter { (habitId) -> habitId == id }
        }

        override fun uniqueId(): String = "query_single_habits"
    }
}

fun generateQueryForSeveralHabits(ids: List<Id>,
                                  completionFromCursorMapper: CompletionFromCursorMapper,
                                  habitFromCursorMapper: HabitFromCursorMapper): Query<Habit, SQLiteDatabase> {
    return object : Query<Habit, SQLiteDatabase> {
        override fun query(readableStorage: SQLiteDatabase): List<Habit> {
            return queryAllHabits(readableStorage, completionFromCursorMapper, habitFromCursorMapper)
                    .filter { (habitId) -> habitId in ids }
        }

        override fun uniqueId(): String = "query_several_habits"
    }
}

fun generateQueryForAllHabits(completionFromCursorMapper: CompletionFromCursorMapper,
                              habitFromCursorMapper: HabitFromCursorMapper): Query<Habit, SQLiteDatabase> {
    return object : Query<Habit, SQLiteDatabase> {
        override fun query(readableStorage: SQLiteDatabase): List<Habit> {
            return queryAllHabits(readableStorage, completionFromCursorMapper, habitFromCursorMapper)
        }

        override fun uniqueId(): String = "query_all_habits"
    }
}

fun subscribeDifferentObservers(repo: HabitsSqliteRepo,
                                habits: List<Habit>,
                                completionFromCursorMapper: CompletionFromCursorMapper,
                                habitFromCursorMapper: HabitFromCursorMapper): List<TestObserver<List<Habit>>> {
    return listOf(
            repo.query(generateQueryForSingleHabit(
                    habits[0].id,
                    completionFromCursorMapper,
                    habitFromCursorMapper
            ))
                    .skip(1) // Skip the first emission
                    .test(),
            repo.query(generateQueryForSeveralHabits(
                    habits.filterIndexed { index, _ -> index % 2 == 0 }.map { (id) -> id },
                    completionFromCursorMapper,
                    habitFromCursorMapper
            ))
                    .skip(1) // Skip the first emission
                    .test(),
            repo.query(generateQueryForAllHabits(completionFromCursorMapper, habitFromCursorMapper))
                    .skip(1) // Skip the first emission
                    .test()
    )
}

fun assertObserversValues(observers: List<TestObserver<List<Habit>>>, habits: List<Habit>) {
    observers[0].assertValue(
            if (habits.isEmpty()) {
                habits
            } else {
                habits.subList(0, 1)
            }
    )
    observers[1].assertValue(habits.filterIndexed { index, _ -> index % 2 == 0 })
    observers[2].assertValue(habits)
}

fun assertHabitMatchesCv(habit: Habit, cv: ContentValues) {
    assertEquals(habit.id.value, cv[HabitEntry.id])
    assertEquals(habit.title.value, cv[HabitEntry.title])
    assertEquals(habit.position, cv[HabitEntry.position])
}

fun assertCompletionMatchesCv(completion: Completion, cv: ContentValues) {
    assertEquals(completion.id.value, cv[CompletionEntry.id])
    assertEquals(completion.habitId.value, cv[CompletionEntry.habitId])
    assertEquals(completion.status.value, cv[CompletionEntry.status])
    assertEquals(completion.date.seconds, cv[CompletionEntry.date])
}