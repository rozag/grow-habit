package com.vsrstudio.common.crash

class StubCrashProvider : CrashProvider {

    override fun report(t: Throwable) {
        t.printStackTrace()
    }

}