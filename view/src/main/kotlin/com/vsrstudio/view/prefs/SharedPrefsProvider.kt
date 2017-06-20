package com.vsrstudio.view.prefs

import android.content.Context
import android.content.SharedPreferences
import com.vsrstudio.view.App

class SharedPrefsProvider : PrefsProvider {

    val PREFS_NAME = "growhabit"
    val prefs: SharedPreferences = App.instance.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun isFirstLaunch(): Boolean {
        return prefs.getBoolean(KEY_FIRST_LAUNCH, true)
    }

    override fun saveFirstLaunch(firstLaunch: Boolean) {
        prefs.edit()
                .putBoolean(KEY_FIRST_LAUNCH, firstLaunch)
                .apply()
    }

}