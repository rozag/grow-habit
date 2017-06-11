package com.vsrstudio.growhabit.app.sections

import com.vsrstudio.growhabit.app.sections.SectionsController.SectionSwitchListener
import org.junit.Test
import org.mockito.Mockito.*

class SectionsControllerTest {

    @Test
    fun selectAssistantWhileBound_assistantSelected() {
        val controller = SectionsController()
        val listener = mock(SectionSwitchListener::class.java)
        controller.bind(listener)
        controller.assistantSectionSelected()
        verify(listener, times(1)).onAssistantSelected()
    }

    @Test
    fun selectHabitsWhileBound_habitsSelected() {
        val controller = SectionsController()
        val listener = mock(SectionSwitchListener::class.java)
        controller.bind(listener)
        controller.habitsSectionSelected()
        verify(listener, times(1)).onHabitsSelected()
    }

    @Test
    fun selectStatisticsWhileBound_statisticsSelected() {
        val controller = SectionsController()
        val listener = mock(SectionSwitchListener::class.java)
        controller.bind(listener)
        controller.statisticsSectionSelected()
        verify(listener, times(1)).onStatisticsSelected()
    }

    @Test
    fun selectAssistantWhileUnbound_selectionIgnored() {
        val controller = SectionsController()
        val listener = mock(SectionSwitchListener::class.java)
        controller.bind(listener)
        controller.unbind()
        controller.assistantSectionSelected()
        verify(listener, times(0)).onAssistantSelected()
    }

    @Test
    fun selectHabitsWhileUnbound_selectionIgnored() {
        val controller = SectionsController()
        val listener = mock(SectionSwitchListener::class.java)
        controller.bind(listener)
        controller.unbind()
        controller.habitsSectionSelected()
        verify(listener, times(0)).onHabitsSelected()
    }

    @Test
    fun selectStatisticsWhileUnbound_selectionIgnored() {
        val controller = SectionsController()
        val listener = mock(SectionSwitchListener::class.java)
        controller.bind(listener)
        controller.unbind()
        controller.statisticsSectionSelected()
        verify(listener, times(0)).onStatisticsSelected()
    }

}