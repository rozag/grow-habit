package com.vsrstudio.view

import android.content.Context
import android.widget.FrameLayout
import timber.log.Timber

class AssistantView(context: Context?) : FrameLayout(context) {

    init {
        Timber.d("init")
        inflate(context, R.layout.view_assistant, this)
    }

}