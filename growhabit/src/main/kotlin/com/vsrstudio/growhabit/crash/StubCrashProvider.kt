package com.vsrstudio.growhabit.crash

object StubCrashProvider : CrashProvider {

    override fun report(t: Throwable) {
        t.printStackTrace()
    }

}