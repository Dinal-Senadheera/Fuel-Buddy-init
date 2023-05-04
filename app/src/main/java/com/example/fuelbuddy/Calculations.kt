package com.example.fuelbuddy

class Calculations(
    private val number1: Int,
    private val number2: Int,
    private val number3: Int
) {
    fun doubleMultiplication() = number1.toDouble() * doubleDivision()
    fun doubleDivision() = number2.toDouble() / number3
    fun toDoubleMultiplication() = (number1 * number2).toDouble()
}