package com.vsrstudio.entity.viewstate

import com.vsrstudio.arch.ViewState
import com.vsrstudio.entity.domain.Habit

data class HabitsViewState(val habits: List<Habit>) : ViewState