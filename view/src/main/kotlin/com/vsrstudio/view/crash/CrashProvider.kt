package com.vsrstudio.view.crash

interface CrashProvider {
    fun report(t: Throwable)
}