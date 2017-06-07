package com.vsrstudio.growhabit.app

import android.app.Application
import com.vsrstudio.growhabit.BuildConfig
import com.vsrstudio.growhabit.crash.CrashProvider
import com.vsrstudio.growhabit.logging.ReleaseTree
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        /*
        * Initialize Timber logging.
        *
        * Later you should change object : CrashProvider {...} stuff to
        * something like FabricCrashProvider or FirebaseCrashProvider
        * */
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else ReleaseTree(object : CrashProvider {
            override fun report(t: Throwable) {}
        }))

        // Do your stuff here (e.g. initialize libraries)
    }

}