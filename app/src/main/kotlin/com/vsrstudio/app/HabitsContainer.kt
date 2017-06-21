package com.vsrstudio.app

import com.vsrstudio.arch.ArchView
import com.vsrstudio.arch.Container
import com.vsrstudio.controller.HabitsController
import com.vsrstudio.entity.useraction.HabitsUserAction
import com.vsrstudio.entity.viewstate.HabitsViewState
import com.vsrstudio.reducer.HabitsReducer

class HabitsContainer(val view: ArchView<HabitsViewState, HabitsUserAction>) :
        Container<HabitsViewState, HabitsUserAction> {

    val controller: HabitsController = HabitsController()
    val reducer: HabitsReducer = HabitsReducer()

    override fun init() {
        controller.subscribeOnUserActions(view.observeUserActions())
        view.subscribeOnViewState(reducer.observeViewState())
    }

    override fun finish() {
        reducer.unsubscribeFromModel()
        controller.unsubscribeFromUserActions()
        view.unsubscribeFromViewState()
    }

}