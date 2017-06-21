package com.vsrstudio.app

import com.vsrstudio.arch.ArchView
import com.vsrstudio.arch.Container
import com.vsrstudio.controller.HabitsController
import com.vsrstudio.entity.useraction.HabitsAction
import com.vsrstudio.entity.viewstate.HabitsViewState
import com.vsrstudio.reducer.HabitsReducer

class HabitsContainer(val view: ArchView<HabitsViewState, HabitsAction>) :
        Container<HabitsViewState, HabitsAction> {

    val controller: HabitsController = HabitsController()
    val reducer: HabitsReducer = HabitsReducer()

    override fun init() {
        controller.subscribeOnActions(view.observeActions())
        view.subscribeOnViewState(reducer.observeViewState())
    }

    override fun finish() {
        reducer.unsubscribeFromModel()
        controller.unsubscribeFromActions()
        view.unsubscribeFromViewState()
    }

}