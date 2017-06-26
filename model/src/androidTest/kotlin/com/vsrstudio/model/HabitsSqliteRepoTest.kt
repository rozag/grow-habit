package com.vsrstudio.model

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.test.InstrumentationRegistry
import android.util.Log
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
        val habitId = Id("test_id")
        val habitToAdd = Habit(
                habitId,
                Title("test_title"),
                listOf(
                        Completion(Id("comp_id_1"), habitId, Completion.Status.DONE, Date(1L)),
                        Completion(Id("comp_id_2"), habitId, Completion.Status.FAIL, Date(2L)),
                        Completion(Id("comp_id_3"), habitId, Completion.Status.EMPTY, Date(3L)),
                        Completion(Id("comp_id_4"), habitId, Completion.Status.SKIP, Date(4L))
                ),
                0
        )
        repo.add(habitToAdd)
        assertEquals(listOf(habitToAdd), queryAllHabits())
    }

    @Test
    fun addMultipleHabits_habitsAdded() {
    }

    @Test
    fun updateSingleHabit_habitUpdated() {
    }

    @Test
    fun updateMultipleHabits_habitsUpdated() {
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
        Log.d("TEST", "habits ids: $habitsIdsSelection")
        val completionsCursor = readableDb.rawQuery(
                "SELECT * " +
                        "FROM ${Table.completion} " +
                        "WHERE ${CompletionEntry.habitId} IN ($habitsIdsSelection);",
                null
        )
        val completions = completionFromCursorMapper.batchMap(completionsCursor)
        completionsCursor.close()
        val habitIdToCompletionListMap = buildHabitIdToCompletionsListMapping(completions)
        Log.d("TEST", "cursor count: ${habitsCursor.count}")
        val habits = habitFromCursorMapper.batchMap(habitsCursor, habitIdToCompletionListMap)
        habitsCursor.close()
        return habits
    }

    private fun buildHabitsIdsSelectionString(habitsCursor: Cursor): String {
        val habitsIdsList = ArrayList<String>(habitsCursor.count)
        val idColInd = habitsCursor.getColumnIndex(HabitEntry.id)
        habitsCursor.moveToFirst()
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
        val habitsIdsSelection = sb.toString()
        return habitsIdsSelection
    }

    private fun buildHabitIdToCompletionsListMapping(completions: List<Completion>): MutableMap<String, MutableList<Completion>> {
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

}