package com.vsrstudio.view.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsrstudio.view.R
import com.vsrstudio.view.ArchBaseFragment
import com.vsrstudio.model.habit.CompositeHabitsRepo
import com.vsrstudio.model.habit.SqlHabitsRepo

class HabitsFragment : ArchBaseFragment<HabitsController, HabitsReducer, HabitsViewState>() {

    override val controller: HabitsController = HabitsController(CompositeHabitsRepo())
    override val reducer: HabitsReducer = HabitsReducer(this, SqlHabitsRepo())

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_habits, container, false)
    }

    override fun updateViewState(viewState: HabitsViewState) {
        // TODO:
    }

}