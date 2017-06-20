package com.vsrstudio.view

abstract class ArchBaseFragment<out C : com.vsrstudio.common.arch.Controller, out R : com.vsrstudio.common.arch.Reducer, in S : com.vsrstudio.common.arch.ViewState> :
        BaseFragment(),
        com.vsrstudio.common.arch.ArchView<C, R, S> {

    override fun onViewCreated(view: android.view.View?, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reducer.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        reducer.unsubscribe()
    }

}