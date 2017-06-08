package com.vsrstudio.growhabit.app.habits

import com.vsrstudio.growhabit.arch.ArchBaseFragment

class HabitsFragment : ArchBaseFragment<HabitsController, HabitsReducer, HabitsViewState>() {

    override val controller: HabitsController = HabitsController()
    override val reducer: HabitsReducer = HabitsReducer(this)

    override fun updateViewState(viewState: HabitsViewState) {
        // TODO:
    }

}