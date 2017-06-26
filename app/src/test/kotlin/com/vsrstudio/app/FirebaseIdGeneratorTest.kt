package com.vsrstudio.app

import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThat
import org.junit.Test

class FirebaseIdGeneratorTest {

    val firebaseIdGenerator: FirebaseIdGenerator = FirebaseIdGenerator()

    @Test
    fun testSameTimeDifferentIds() {
        val timestamp = 1468418909016L
        val tsAsString = "-KMZ_aCN"
        val ids = (1..100000).map { firebaseIdGenerator.generateId(instant = timestamp) }
        assertThat(ids.sorted(), equalTo(ids))
        assertThat(ids.map { it.substring(0..7) }, equalTo(ids.map { tsAsString }))
        assertThat(ids.distinct().size, equalTo(ids.size))
    }

    @Test
    fun testSameTimeDifferentStates() {
        val initialState = FirebaseIdGenerator.State()
        val timestamp = 1468418909016L
        val result = firebaseIdGenerator.generateNextId(initialState, timestamp)
        assertThat(result.nextState.lastInstant, equalTo(timestamp))
        assertNotEquals(result.nextState.lastRandChars, initialState.lastRandChars)
    }

}