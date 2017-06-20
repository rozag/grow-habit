package com.vsrstudio.view.model

import java.util.*
import java.util.concurrent.TimeUnit

data class Date(val seconds: Long) {

    companion object {
        fun current(): Date {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    0,
                    0,
                    0
            )
            val seconds = TimeUnit.MILLISECONDS.toSeconds(calendar.timeInMillis)
            return Date(seconds)
        }
    }

}