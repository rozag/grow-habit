package com.vsrstudio.entity.domain

data class Date(val seconds: Long) {

    companion object {
        fun current(): com.vsrstudio.entity.domain.Date {
            val calendar = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"))
            calendar.set(
                    calendar.get(java.util.Calendar.YEAR),
                    calendar.get(java.util.Calendar.MONTH),
                    calendar.get(java.util.Calendar.DAY_OF_MONTH),
                    0,
                    0,
                    0
            )
            val seconds = java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(calendar.timeInMillis)
            return com.vsrstudio.entity.domain.Date(seconds)
        }
    }

}