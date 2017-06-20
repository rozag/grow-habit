package com.vsrstudio.entity

data class Habit(val id: Id, val title: Title, val completions: Map<Date, Completion>) {
    fun rename(newTitle: Title) = com.vsrstudio.entity.Habit(id, newTitle, completions)
}