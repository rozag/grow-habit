package com.vsrstudio.growhabit.prefs

/**
 * An abstraction for the preferences provider (like SharedPreferences).
 *
 * Implement it with something like AndroidPrefsProvider
 * or BinaryPrefsProvider (https://github.com/iamironz/binaryprefs).
 * The implementation of this interface can mimic the SharedPreferences
 * interface or it can contain more domain-related methods like uuid(), saveUuid(), etc.
 *
 * We don't want to have Android framework imports in our
 * classes for better testing, so we'll use this interface.
 *
 * Feel free to extend it for your own needs.
 */
interface PrefsProvider {
}