package com.vsrstudio.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.test.InstrumentationRegistry
import com.vsrstudio.arch.Query
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.DatabaseInfo
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
        assertEquals(
                listOf(habitToAdd),
                queryAllHabits(
                        dbOpenHelper.readableDatabase,
                        completionFromCursorMapper,
                        habitFromCursorMapper
                )
        )
    }

    @Test
    fun addMultipleHabits_habitsAdded() {
        val habitsList = generateHabitsList()
        repo.add(habitsList)
        assertEquals(
                habitsList,
                queryAllHabits(
                        dbOpenHelper.readableDatabase,
                        completionFromCursorMapper,
                        habitFromCursorMapper
                )
        )
    }

    @Test
    fun updateSingleHabit_habitUpdated() {
        val oldIndex = 0
        val habit = generateHabit(oldIndex)
        addHabits(
                dbOpenHelper.writableDatabase,
                listOf(habit),
                habitToContentValuesMapper,
                completionToContentValuesMapper
        )
        val updatedHabit = generateUpdatedHabit(habit, oldIndex)
        repo.update(updatedHabit)
        assertEquals(
                listOf(updatedHabit),
                queryAllHabits(
                        dbOpenHelper.readableDatabase,
                        completionFromCursorMapper,
                        habitFromCursorMapper
                )
        )
    }

    @Test
    fun updateMultipleHabits_habitsUpdated() {
        val habits = generateHabitsList()
        addHabits(
                dbOpenHelper.writableDatabase,
                habits,
                habitToContentValuesMapper,
                completionToContentValuesMapper
        )
        val updatedHabits = generateUpdatedHabitsList(habits)
        repo.update(updatedHabits)
        assertEquals(
                updatedHabits,
                queryAllHabits(
                        dbOpenHelper.readableDatabase,
                        completionFromCursorMapper,
                        habitFromCursorMapper
                )
        )
    }

    @Test
    fun removeSingleHabit_habitRemoved() {
        val habit = generateHabit()
        addHabits(
                dbOpenHelper.writableDatabase,
                listOf(habit),
                habitToContentValuesMapper,
                completionToContentValuesMapper
        )
        repo.remove(habit)
        assertEquals(
                listOf<Habit>(),
                queryAllHabits(
                        dbOpenHelper.readableDatabase,
                        completionFromCursorMapper,
                        habitFromCursorMapper
                )
        )
    }

    @Test
    fun queryAllHabits_allHabitsReturned() {
        val habits = generateHabitsList()
        addHabits(
                dbOpenHelper.writableDatabase,
                habits,
                habitToContentValuesMapper,
                completionToContentValuesMapper
        )
        val allHabitsQuery = object : Query<Habit, SQLiteDatabase> {
            override fun query(readableStorage: SQLiteDatabase): List<Habit> {
                return queryAllHabits(
                        dbOpenHelper.writableDatabase,
                        completionFromCursorMapper,
                        habitFromCursorMapper
                )
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
        val observers = subscribeDifferentObservers(
                repo,
                habitsList,
                completionFromCursorMapper,
                habitFromCursorMapper
        )
        repo.add(habitToAdd)
        assertObserversValues(observers, habitsList)
    }

    @Test
    fun addMultipleHabits_observersNotified() {
        val habitsList = generateHabitsList()
        val observers = subscribeDifferentObservers(
                repo,
                habitsList,
                completionFromCursorMapper,
                habitFromCursorMapper
        )
        repo.add(habitsList)
        assertObserversValues(observers, habitsList)
    }

    @Test
    fun updateSingleHabit_observersNotified() {
        val oldIndex = 0
        val habit = generateHabit(oldIndex)
        addHabits(
                dbOpenHelper.writableDatabase,
                listOf(habit),
                habitToContentValuesMapper,
                completionToContentValuesMapper
        )
        val updatedHabit = generateUpdatedHabit(habit, oldIndex)
        val habits = listOf(updatedHabit)
        val observers = subscribeDifferentObservers(
                repo,
                habits,
                completionFromCursorMapper,
                habitFromCursorMapper
        )
        repo.update(updatedHabit)
        assertObserversValues(observers, habits)
    }

    @Test
    fun updateMultipleHabits_observersNotified() {
        val habits = generateHabitsList()
        addHabits(
                dbOpenHelper.writableDatabase,
                habits,
                habitToContentValuesMapper,
                completionToContentValuesMapper
        )
        val updatedHabits = generateUpdatedHabitsList(habits)
        val observers = subscribeDifferentObservers(
                repo,
                updatedHabits,
                completionFromCursorMapper,
                habitFromCursorMapper
        )
        repo.update(updatedHabits)
        assertObserversValues(observers, updatedHabits)
    }

    @Test
    fun removeSingleHabit_observersNotified() {
        val habit = generateHabit()
        val habitList = listOf(habit)
        addHabits(
                dbOpenHelper.writableDatabase,
                habitList,
                habitToContentValuesMapper,
                completionToContentValuesMapper
        )
        val observers = subscribeDifferentObservers(
                repo,
                habitList,
                completionFromCursorMapper,
                habitFromCursorMapper
        )
        repo.remove(habit)
        assertObserversValues(observers, listOf())
    }

}