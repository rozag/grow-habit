package com.vsrstudio.entity.domain

data class Habit(val id: Id, val title: Title, val completions: List<Completion>, val position: Int) :
        Comparable<Habit> {

    fun rename(newTitle: Title) = Habit(id, newTitle, completions, position)

    fun addCompletion(completion: Completion) = Habit(
            id,
            title,
            completions + completion,
            position
    )

    fun changePosition(newPosition: Int) = Habit(
            id,
            title,
            completions,
            newPosition
    )

    override fun compareTo(other: Habit): Int = when {
        position < other.position -> -1
        position == other.position -> 0
        else -> 1
    }

}