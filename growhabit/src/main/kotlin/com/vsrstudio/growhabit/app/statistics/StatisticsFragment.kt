package com.vsrstudio.growhabit.app.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsrstudio.growhabit.R
import com.vsrstudio.growhabit.arch.ArchBaseFragment

class StatisticsFragment : ArchBaseFragment<StatisticsController, StatisticsReducer, StatisticsViewState>() {

    override val controller: StatisticsController = StatisticsController()
    override val reducer: StatisticsReducer = StatisticsReducer(this)

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun updateViewState(viewState: StatisticsViewState) {
        // TODO:
    }

}