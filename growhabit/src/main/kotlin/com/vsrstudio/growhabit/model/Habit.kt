package com.vsrstudio.growhabit.model

import java.util.Date

data class Habit(val id: Id, val title: Title, val completions: Map<Date, Completion>)