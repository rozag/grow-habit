package com.vsrstudio.growhabit.app.habits

import com.vsrstudio.growhabit.arch.ViewState
import com.vsrstudio.growhabit.model.Habit

data class HabitsViewState(val habits: List<Habit>) : ViewState