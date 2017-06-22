package com.vsrstudio.app

import com.vsrstudio.arch.ArchView
import com.vsrstudio.arch.Container
import com.vsrstudio.controller.HabitsController
import com.vsrstudio.entity.useraction.HabitsAction
import com.vsrstudio.entity.viewstate.HabitsViewState
import com.vsrstudio.reducer.HabitsReducer

class HabitsContainer(appContainer: AppContainer,
                      val view: ArchView<HabitsViewState, HabitsAction>) :
        Container<HabitsViewState, HabitsAction> {

    val controller: HabitsController = HabitsController(appContainer.habitsRepo)
    val reducer: HabitsReducer = HabitsReducer()

    override fun init() {
        controller.subscribeOnActions(view.observeActions())
        view.subscribeOnViewState(reducer.observeViewState())
    }

    override fun finish() {
        reducer.unsubscribeFromModel()
        view.stopEmittingActions()
        view.unsubscribeFromViewState()
    }

}