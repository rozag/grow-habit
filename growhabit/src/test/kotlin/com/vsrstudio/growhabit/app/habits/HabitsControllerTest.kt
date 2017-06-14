package com.vsrstudio.growhabit.app.habits

import com.vsrstudio.growhabit.model.*
import com.vsrstudio.growhabit.repo.habit.HabitsRepo
import org.junit.Test
import org.mockito.Mockito.*

class HabitsControllerTest {

    @Test
    fun habitCompletionChanged_repoUpdateCalled() {
        val habitRepo = mock(HabitsRepo::class.java)
        val habitsController = HabitsController(habitRepo)
        val habit = Habit(
                Id("test_id"),
                Title("test_title"),
                mapOf()
        )
        val completion = Completion.DONE
        habitsController.onHabitCompletionChanged(habit, completion)
        val expectedHabit = Habit(
                Id("test_id"),
                Title("test_title"),
                mapOf(Date.current() to completion)
        )
        verify(habitRepo, times(1)).update(expectedHabit)
    }

}