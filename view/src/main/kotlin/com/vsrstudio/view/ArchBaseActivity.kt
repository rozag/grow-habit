package com.vsrstudio.view

import android.os.Bundle
import com.vsrstudio.common.arch.ArchView
import com.vsrstudio.common.arch.Controller
import com.vsrstudio.common.arch.Reducer
import com.vsrstudio.common.arch.ViewState
import com.vsrstudio.view.BaseActivity

abstract class ArchBaseActivity<out C : Controller, out R : Reducer, in S : ViewState> :
        BaseActivity(),
        ArchView<C, R, S> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reducer.subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        reducer.unsubscribe()
    }

}