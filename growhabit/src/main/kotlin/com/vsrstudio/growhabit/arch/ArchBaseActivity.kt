package com.vsrstudio.growhabit.arch

import android.os.Bundle
import com.vsrstudio.growhabit.BaseActivity

abstract class ArchBaseActivity<out C : Controller, out R : Reducer, in S : ViewState> :
        BaseActivity(),
        ArchView<C, R, S> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reducer.bind()
    }

    override fun onDestroy() {
        super.onDestroy()
        reducer.unbind()
    }

}