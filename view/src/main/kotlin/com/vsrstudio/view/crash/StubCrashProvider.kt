package com.vsrstudio.view.crash

class StubCrashProvider : CrashProvider {

    override fun report(t: Throwable) {
        t.printStackTrace()
    }

}