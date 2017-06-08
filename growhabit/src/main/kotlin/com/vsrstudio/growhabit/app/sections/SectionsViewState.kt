package com.vsrstudio.growhabit.app.sections

import com.vsrstudio.growhabit.arch.ViewState

sealed class SectionsViewState : ViewState {
    class AssistantState : SectionsViewState()
    class HabitsState : SectionsViewState()
    class StatisticsState : SectionsViewState()
}