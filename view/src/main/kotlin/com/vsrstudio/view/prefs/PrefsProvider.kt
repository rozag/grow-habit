package com.vsrstudio.view.prefs

interface PrefsProvider {
    fun isFirstLaunch(): Boolean
    fun saveFirstLaunch(firstLaunch: Boolean)
}