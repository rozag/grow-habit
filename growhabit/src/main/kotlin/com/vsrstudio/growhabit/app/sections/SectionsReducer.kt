package com.vsrstudio.growhabit.app.sections

import com.vsrstudio.growhabit.app.sections.SectionsController.SectionSwitchListener
import com.vsrstudio.growhabit.app.sections.SectionsViewState.*
import com.vsrstudio.growhabit.arch.ArchView
import com.vsrstudio.growhabit.arch.Reducer

class SectionsReducer(override val view: ArchView<SectionsController, SectionsReducer, SectionsViewState>,
                      val controller: SectionsController) : Reducer, SectionSwitchListener {

    override fun bind() = controller.bind(this)
    override fun unbind() = controller.unbind()
    override fun onAssistantSelected() = view.updateViewState(AssistantState)
    override fun onHabitsSelected() = view.updateViewState(HabitsState)
    override fun onStatisticsSelected() = view.updateViewState(StatisticsState)

}