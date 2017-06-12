package com.vsrstudio.growhabit.crash

class StubCrashProvider : CrashProvider {

    override fun report(t: Throwable) {
        t.printStackTrace()
    }

}