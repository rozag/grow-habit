package com.vsrstudio.view.app.sections

import com.vsrstudio.view.app.sections.SectionsController.SectionSwitchListener
import com.vsrstudio.view.app.sections.SectionsViewState.*
import com.vsrstudio.view.arch.ArchView
import com.vsrstudio.view.arch.Reducer

class SectionsReducer(override val view: ArchView<SectionsController, SectionsReducer, SectionsViewState>,
                      val controller: SectionsController) : Reducer, SectionSwitchListener {

    override fun subscribe() = controller.subscribe(this)
    override fun unsubscribe() = controller.unsubscibe()
    override fun onAssistantSelected() = view.updateViewState(AssistantState)
    override fun onHabitsSelected() = view.updateViewState(HabitsState)
    override fun onStatisticsSelected() = view.updateViewState(StatisticsState)

}