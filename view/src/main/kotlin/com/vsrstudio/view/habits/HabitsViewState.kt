package com.vsrstudio.view.habits

import com.vsrstudio.common.arch.ViewState
import com.vsrstudio.entity.Habit

data class HabitsViewState(val habits: List<Habit>) : ViewState