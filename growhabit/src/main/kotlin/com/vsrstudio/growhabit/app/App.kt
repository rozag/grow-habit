package com.vsrstudio.growhabit.app

import android.app.Application
import com.vsrstudio.growhabit.BuildConfig
import com.vsrstudio.growhabit.crash.StubCrashProvider
import com.vsrstudio.growhabit.logging.ReleaseTree
import timber.log.Timber

class App : Application() {

    companion object Instance {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        val tree = if (BuildConfig.DEBUG) {
            Timber.DebugTree()
        } else {
            ReleaseTree(StubCrashProvider)
        }
        Timber.plant(tree)
    }

}