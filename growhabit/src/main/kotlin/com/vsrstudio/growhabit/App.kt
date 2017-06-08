package com.vsrstudio.growhabit

import android.app.Application
import com.vsrstudio.growhabit.BuildConfig
import com.vsrstudio.growhabit.crash.StubCrashProvider
import com.vsrstudio.growhabit.logging.ReleaseTree
import timber.log.Timber

class App : android.app.Application() {

    companion object Instance {
        lateinit var instance: com.vsrstudio.growhabit.App
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        val tree = if (com.vsrstudio.growhabit.BuildConfig.DEBUG) {
            timber.log.Timber.DebugTree()
        } else {
            com.vsrstudio.growhabit.logging.ReleaseTree(com.vsrstudio.growhabit.crash.StubCrashProvider)
        }
        timber.log.Timber.plant(tree)
    }

}