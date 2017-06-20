package com.vsrstudio.view.app.habits

import com.vsrstudio.view.arch.ViewState
import com.vsrstudio.view.model.Habit

data class HabitsViewState(val habits: List<Habit>) : ViewState