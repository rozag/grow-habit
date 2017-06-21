package com.vsrstudio.view

import android.content.Context
import android.widget.FrameLayout

class AssistantView(context: Context?) : FrameLayout(context) {

    init {
        inflate(context, R.layout.view_assistant, this)
    }

}