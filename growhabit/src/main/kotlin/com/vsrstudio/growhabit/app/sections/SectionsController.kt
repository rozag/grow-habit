package com.vsrstudio.growhabit.app.sections

import com.vsrstudio.growhabit.arch.Controller

class SectionsController : Controller {

    private var listener: SectionSwitchListener? = null

    override fun start() = habitsSectionSelected()

    fun bind(listener: SectionSwitchListener) {
        this.listener = listener
    }

    fun unbind() {
        this.listener = null
    }

    fun assistantSectionSelected() {
        listener?.onAssistantSelected()
    }

    fun habitsSectionSelected() {
        listener?.onHabitsSelected()
    }

    fun statisticsSectionSelected() {
        listener?.onStatisticsSelected()
    }

    interface SectionSwitchListener {
        fun onAssistantSelected()
        fun onHabitsSelected()
        fun onStatisticsSelected()
    }

}