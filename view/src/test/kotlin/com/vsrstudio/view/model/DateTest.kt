package com.vsrstudio.view.model

import com.vsrstudio.entity.Date
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