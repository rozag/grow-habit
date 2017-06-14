package com.vsrstudio.growhabit.app.habits

import com.vsrstudio.growhabit.arch.Controller
import com.vsrstudio.growhabit.model.Completion
import com.vsrstudio.growhabit.model.Date
import com.vsrstudio.growhabit.model.Habit
import com.vsrstudio.growhabit.repo.habit.HabitsRepo

class HabitsController(val habitsRepo: HabitsRepo<*>) : Controller {

    fun onHabitCompletionChanged(habit: Habit, completion: Completion) {
        val currentDate = Date.current()
        val newCompletions = mutableMapOf<Date, Completion>()
        newCompletions.apply {
            putAll(habit.completions)
            put(currentDate, completion)
        }
        val updatedHabit = Habit(habit.id, habit.title, newCompletions)
        habitsRepo.update(updatedHabit)
    }

}