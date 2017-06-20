package com.vsrstudio.common.analytics

interface AnalyticsProvider {
    fun event(event: String, params: Map<String, Any> = hashMapOf())
}