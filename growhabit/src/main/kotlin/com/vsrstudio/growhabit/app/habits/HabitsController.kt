package com.vsrstudio.growhabit.app.habits

import com.vsrstudio.growhabit.arch.Controller
import com.vsrstudio.growhabit.model.*
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

    fun onHabitAdded(title: Title) {
        val currentDate = Date.current()
        val newCompletions = mapOf<Date, Completion>(currentDate to Completion.EMPTY)
        val newHabit = Habit(Id.EMPTY, title, newCompletions)
        habitsRepo.add(newHabit)
    }

}