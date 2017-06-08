package com.vsrstudio.growhabit.app.sections

import android.content.Context
import android.content.Intent
import com.vsrstudio.growhabit.R
import com.vsrstudio.growhabit.arch.ArchBaseActivity

class SectionsActivity : ArchBaseActivity<SectionsController, SectionsReducer, SectionsViewState>() {

    companion object {
        fun makeIntent(context: Context): Intent {
            val intent = Intent(context, SectionsActivity::class.java)
            return intent
        }
    }

    override val layoutResourceId = R.layout.activity_main
    override val toolbarTitleId = R.string.app_name
    override val displayHomeAsUp = false
    override val homeButtonEnabled = false

    override val controller: SectionsController = SectionsController()
    override val reducer: SectionsReducer = SectionsReducer(this, controller)

    override fun updateViewState(viewState: SectionsViewState) {
    }

}