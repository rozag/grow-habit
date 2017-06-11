package com.vsrstudio.growhabit.app.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsrstudio.growhabit.R
import com.vsrstudio.growhabit.arch.ArchBaseFragment

class HabitsFragment : ArchBaseFragment<HabitsController, HabitsReducer, HabitsViewState>() {

    override val controller: HabitsController = HabitsController()
    override val reducer: HabitsReducer = HabitsReducer(this)

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_habits, container, false)
    }

    override fun updateViewState(viewState: HabitsViewState) {
        // TODO:
    }

}