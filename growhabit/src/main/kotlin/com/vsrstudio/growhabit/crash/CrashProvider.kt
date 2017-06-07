package com.vsrstudio.growhabit.crash

interface CrashProvider {
    fun report(t: Throwable)
}