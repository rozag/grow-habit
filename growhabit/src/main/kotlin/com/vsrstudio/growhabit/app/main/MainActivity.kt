package com.vsrstudio.growhabit.app.main

import android.os.Bundle
import com.vsrstudio.growhabit.R
import com.vsrstudio.growhabit.app.BaseActivity

class MainActivity : BaseActivity() {

    override val layoutResourceId = R.layout.activity_main
    override val toolbarTitleId = R.string.app_name
    override val displayHomeAsUp = false
    override val homeButtonEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}