package com.vsrstudio.growhabit.crash

/**
 * An abstraction for the crash reporting tool.
 *
 * Implement it with something like FabricCrashProvider
 * or FirebaseCrashProvider.
 *
 * Feel free to extend it for your own needs.
 */
interface CrashProvider {
    fun report(t: Throwable)
}