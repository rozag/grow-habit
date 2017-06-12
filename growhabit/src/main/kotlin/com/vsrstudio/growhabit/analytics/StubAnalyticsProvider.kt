package com.vsrstudio.growhabit.analytics

import timber.log.Timber

class StubAnalyticsProvider : AnalyticsProvider {

    override fun event(event: String, params: Map<String, Any>) {
        Timber.d("event: $event, params: $params")
    }

}