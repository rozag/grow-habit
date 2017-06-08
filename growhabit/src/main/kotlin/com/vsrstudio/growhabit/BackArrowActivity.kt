package com.vsrstudio.growhabit

/**
 * Simple base class for activities with home toolbar arrow.
 * [BackArrowActivity.onBackPressed] is invoked on the arrow click,
 * so you can override [BackArrowActivity.onBackPressed] for customization.
 */
abstract class BackArrowActivity : BaseActivity() {

    override val displayHomeAsUp = true
    override val homeButtonEnabled = true

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar?.setNavigationOnClickListener { onBackPressed() }
    }

}
