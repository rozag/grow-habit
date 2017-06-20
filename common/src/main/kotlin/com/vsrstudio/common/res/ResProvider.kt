package com.vsrstudio.common.res

import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.StringRes

interface ResProvider {
    fun string(@StringRes stringId: Int): String
    @ColorInt fun color(@ColorRes colorId: Int): Int
}