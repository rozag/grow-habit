package com.vsrstudio.growhabit.arch

import android.os.Bundle
import android.view.View
import com.vsrstudio.growhabit.BaseFragment

abstract class ArchBaseFragment<out C : Controller, out R : Reducer, in S : ViewState> :
        BaseFragment(),
        ArchView<C, R, S> {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reducer.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        reducer.unsubscribe()
    }

}