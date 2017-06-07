package com.vsrstudio.growhabit.analytics

/**
 * An abstraction for the analytics provider.
 *
 * Implement it with something like GoogleAnalyticsProvider
 * or FirebaseAnalyticsProvider.
 *
 * Feel free to extend it for your own needs.
 */
interface AnalyticsProvider {
    fun event(event: String, params: Map<String, Any> = hashMapOf())
}