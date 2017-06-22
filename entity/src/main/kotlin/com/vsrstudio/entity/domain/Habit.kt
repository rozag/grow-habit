package com.vsrstudio.entity.domain

data class Habit(val id: Id, val title: Title, val completions: Map<Date, Completion>) {

    fun rename(newTitle: Title) = Habit(id, newTitle, completions)

    fun addCompletion(date: Date, completion: Completion) = Habit(
            id,
            title,
            completions + mapOf(date to completion)
    )

}