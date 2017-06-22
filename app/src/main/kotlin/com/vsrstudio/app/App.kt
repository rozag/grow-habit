package com.vsrstudio.app

import android.app.Application
import com.vsrstudio.common.crash.StubCrashProvider
import com.vsrstudio.common.logging.ReleaseTree
import timber.log.Timber

class App : Application() {

    companion object Instance {
        lateinit var instance: App
    }

    val appContainer: AppContainer = AppContainer(applicationContext)

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        val tree = if (BuildConfig.DEBUG) {
            Timber.DebugTree()
        } else {
            ReleaseTree(StubCrashProvider())
        }
        Timber.plant(tree)
    }

}