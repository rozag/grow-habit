package com.vsrstudio.common.crash

interface CrashProvider {
    fun report(t: Throwable)
}