package com.vsrstudio.growhabit.analytics

interface AnalyticsProvider {
    fun event(event: String, params: Map<String, Any> = hashMapOf())
}