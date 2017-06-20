package com.vsrstudio.view.sections

import com.vsrstudio.view.sections.SectionsController.SectionSwitchListener
import com.vsrstudio.view.sections.SectionsViewState.*
import com.vsrstudio.common.arch.ArchView
import com.vsrstudio.common.arch.Reducer

class SectionsReducer(override val view: ArchView<SectionsController, SectionsReducer, SectionsViewState>,
                      val controller: SectionsController) : Reducer, SectionSwitchListener {

    override fun subscribe() = controller.subscribe(this)
    override fun unsubscribe() = controller.unsubscibe()
    override fun onAssistantSelected() = view.updateViewState(AssistantState)
    override fun onHabitsSelected() = view.updateViewState(HabitsState)
    override fun onStatisticsSelected() = view.updateViewState(StatisticsState)

}