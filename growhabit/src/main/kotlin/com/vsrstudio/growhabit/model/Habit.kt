package com.vsrstudio.growhabit.model

data class Habit(val id: Id, val title: Title, val completions: Map<Date, Completion>)