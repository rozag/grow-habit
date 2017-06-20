package com.vsrstudio.common.prefs

import android.content.SharedPreferences

class SharedPrefsProvider(val prefs: SharedPreferences) : PrefsProvider {

    val PREFS_NAME = "growhabit"

    override fun isFirstLaunch(): Boolean {
        return prefs.getBoolean(KEY_FIRST_LAUNCH, true)
    }

    override fun saveFirstLaunch(firstLaunch: Boolean) {
        prefs.edit()
                .putBoolean(KEY_FIRST_LAUNCH, firstLaunch)
                .apply()
    }

}