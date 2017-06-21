package com.vsrstudio.entity.domain

import com.vsrstudio.entity.domain.Date
import junit.framework.Assert.assertEquals
import org.junit.Test

class DateTest {

    @Test
    fun currentDateEquals() {
        val date1 = Date.current()
        val date2 = Date.current()
        assertEquals(date1, date2)
    }

}