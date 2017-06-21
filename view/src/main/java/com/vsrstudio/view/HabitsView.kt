package com.vsrstudio.view

import android.content.Context
import android.widget.FrameLayout

class HabitsView(context: Context?) : FrameLayout(context) {

    init {
        inflate(context, R.layout.view_habits, this)
    }

}