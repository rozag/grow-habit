package com.vsrstudio.view.app.habits

import com.vsrstudio.view.arch.Controller
import com.vsrstudio.view.model.*
import com.vsrstudio.view.repo.habit.HabitsRepo

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

    fun onHabitRemoved(habitToRemove: Habit) {
        habitsRepo.remove(habitToRemove)
    }

    fun onHabitRenamed(habitToRename: Habit, newTitle: Title) {
        val updatedHabit = habitToRename.rename(newTitle)
        habitsRepo.update(updatedHabit)
    }

}