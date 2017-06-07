package com.vsrstudio.growhabit.prefs

interface PrefsProvider {
    fun isFirstLaunch(): Boolean
    fun saveFirstLaunch(firstLaunch: Boolean)
}