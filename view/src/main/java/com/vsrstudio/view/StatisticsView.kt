package com.vsrstudio.view

import android.content.Context
import android.view.View
import android.widget.FrameLayout

class StatisticsView(context: Context?) : FrameLayout(context) {

    init {
        View.inflate(context, R.layout.view_statistics, this)
    }

}