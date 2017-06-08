package com.vsrstudio.growhabit.app.splash

import android.os.Bundle
import com.vsrstudio.growhabit.BaseActivity
import com.vsrstudio.growhabit.app.sections.SectionsActivity

class SplashActivity : BaseActivity() {

    override val layoutResourceId = 0
    override val toolbarTitleId = 0
    override val displayHomeAsUp = false
    override val homeButtonEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(SectionsActivity.makeIntent(this))
        finish()
    }

}