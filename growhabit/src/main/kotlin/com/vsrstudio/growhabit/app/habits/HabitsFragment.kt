package com.vsrstudio.growhabit.app.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsrstudio.growhabit.R
import com.vsrstudio.growhabit.arch.ArchBaseFragment
import com.vsrstudio.growhabit.repo.habit.CompositeHabitsRepo
import com.vsrstudio.growhabit.repo.habit.SqlHabitsRepo

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