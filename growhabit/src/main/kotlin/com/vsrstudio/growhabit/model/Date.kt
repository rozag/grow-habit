package com.vsrstudio.growhabit.model

import java.util.*

data class Date(val millis: Long) {

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
            return Date(calendar.timeInMillis)
        }
    }

}