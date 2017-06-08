package com.vsrstudio.growhabit.res

import android.support.v4.content.ContextCompat
import com.vsrstudio.growhabit.App

object AndroidResProvider : ResProvider {

    override fun string(stringId: Int): String {
        return App.instance.getString(stringId)
    }

    override fun color(colorId: Int): Int {
        return ContextCompat.getColor(App.instance, colorId)
    }

}