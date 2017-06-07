package com.vsrstudio.growhabit.analytics

import timber.log.Timber

object StubAnalyticsProvider : AnalyticsProvider {

    override fun event(event: String, params: Map<String, Any>) {
        Timber.d("event: $event, params: $params")
    }

}