package com.vsrstudio.view

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import timber.log.Timber

class StatisticsView(context: Context?) : FrameLayout(context) {

    init {
        Timber.d("init")
        View.inflate(context, R.layout.view_statistics, this)
    }

}