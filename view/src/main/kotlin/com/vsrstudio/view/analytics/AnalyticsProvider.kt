package com.vsrstudio.view.analytics

interface AnalyticsProvider {
    fun event(event: String, params: Map<String, Any> = hashMapOf())
}