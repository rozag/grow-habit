package com.vsrstudio.view.app.sections

import com.vsrstudio.view.arch.Controller

class SectionsController : Controller {

    private var listener: SectionSwitchListener? = null

    fun subscribe(listener: SectionSwitchListener) {
        this.listener = listener
    }

    fun unsubscibe() {
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