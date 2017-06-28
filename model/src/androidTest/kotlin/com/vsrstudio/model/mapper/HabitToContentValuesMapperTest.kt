package com.vsrstudio.model.mapper

import android.content.ContentValues
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.model.assertHabitMatchesCv
import com.vsrstudio.model.generateHabit
import com.vsrstudio.model.generateHabitsList
import junit.framework.Assert.assertEquals
import org.junit.Test

class HabitToContentValuesMapperTest {

    private val mapper = HabitToContentValuesMapper()

    @Test
    fun mapHabit_correctCvReturned() {
        val habit = generateHabit()
        val cv = mapper.map(habit)
        assertHabitMatchesCv(habit, cv)
    }

    @Test
    fun batchMapHabits_correctCvListReturned() {
        val habits = generateHabitsList()
        val cvList = mapper.batchMap(habits)
        habits.zip(cvList)
                .forEach { (habit, cv) -> assertHabitMatchesCv(habit, cv) }
    }

    @Test
    fun batchMapEmptyList_emptyCvListReturned() {
        val habits = listOf<Habit>()
        val cvList = mapper.batchMap(habits)
        assertEquals(cvList, listOf<ContentValues>())
    }

}