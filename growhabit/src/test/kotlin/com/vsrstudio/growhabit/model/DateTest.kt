package com.vsrstudio.growhabit.model

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