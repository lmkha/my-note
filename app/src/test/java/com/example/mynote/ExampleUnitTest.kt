package com.example.mynote

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun addition_isNotCorrect() {
        assertNotEquals(5, 2 + 2)
    }

    @Test
    fun minus_isCorrect() {
        assertEquals(0, 2 - 2)
    }

    @Test
    fun minus_isNotCorrect() {
        assertNotEquals(1, 2 - 2)
    }
}