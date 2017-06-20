package com.vsrstudio.common.res

import android.content.Context
import android.support.v4.content.ContextCompat

class AndroidResProvider(val context: Context) : ResProvider {

    override fun string(stringId: Int): String {
        return context.getString(stringId)
    }

    override fun color(colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

}