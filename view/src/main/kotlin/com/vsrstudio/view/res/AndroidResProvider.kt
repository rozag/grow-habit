package com.vsrstudio.view.res

import android.support.v4.content.ContextCompat
import com.vsrstudio.view.App

class AndroidResProvider : ResProvider {

    override fun string(stringId: Int): String {
        return App.instance.getString(stringId)
    }

    override fun color(colorId: Int): Int {
        return ContextCompat.getColor(App.instance, colorId)
    }

}