package com.vsrstudio.growhabit.app.sections

import com.vsrstudio.growhabit.arch.ArchView
import junit.framework.Assert.assertTrue
import org.junit.Test

class SectionsReducerTest {

    @Test
    fun assistantSelected_assistantState() {
        val view = object : ArchView<SectionsController, SectionsReducer, SectionsViewState> {
            override val controller: SectionsController = SectionsController()
            override val reducer: SectionsReducer = SectionsReducer(this, controller)

            override fun updateViewState(viewState: SectionsViewState) {
                assertTrue(viewState == SectionsViewState.AssistantState)
            }
        }
        view.reducer.onAssistantSelected()
    }

    @Test
    fun habitsSelected_habitsState() {
        val view = object : ArchView<SectionsController, SectionsReducer, SectionsViewState> {
            override val controller: SectionsController = SectionsController()
            override val reducer: SectionsReducer = SectionsReducer(this, controller)

            override fun updateViewState(viewState: SectionsViewState) {
                assertTrue(viewState == SectionsViewState.HabitsState)
            }
        }
        view.reducer.onHabitsSelected()
    }

    @Test
    fun statisticsSelected_statisticsState() {
        val view = object : ArchView<SectionsController, SectionsReducer, SectionsViewState> {
            override val controller: SectionsController = SectionsController()
            override val reducer: SectionsReducer = SectionsReducer(this, controller)

            override fun updateViewState(viewState: SectionsViewState) {
                assertTrue(viewState == SectionsViewState.StatisticsState)
            }
        }
        view.reducer.onStatisticsSelected()
    }

}