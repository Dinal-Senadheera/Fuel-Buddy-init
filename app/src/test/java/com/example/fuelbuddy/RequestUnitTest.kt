package com.example.fuelbuddy

import org.junit.Test

import org.junit.Assert.*

val calculations = Calculations(100,99, 100)
class ExampleUnitTest {
    @Test
    fun doubleDivision_isCorrect() {
        assertEquals(0.99, calculations.doubleDivision(), 0.001)
    }
    @Test
    fun doubleMultiplication_isCorrect() {
        assertEquals(99.0, calculations.doubleMultiplication(),0.001)
    }
}