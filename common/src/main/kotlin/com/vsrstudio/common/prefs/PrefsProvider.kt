package com.vsrstudio.common.prefs

interface PrefsProvider {
    fun isFirstLaunch(): Boolean
    fun saveFirstLaunch(firstLaunch: Boolean)
}