package com.vsrstudio.growhabit.app.statistics

import com.vsrstudio.growhabit.arch.ArchBaseFragment

class StatisticsFragment : ArchBaseFragment<StatisticsController, StatisticsReducer, StatisticsViewState>() {

    override val controller: StatisticsController = StatisticsController()
    override val reducer: StatisticsReducer = StatisticsReducer(this)

    override fun updateViewState(viewState: StatisticsViewState) {
        // TODO:
    }

}