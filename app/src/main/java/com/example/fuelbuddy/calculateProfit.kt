package com.example.fuelbuddy

class calculateProfit (
    private val num1 : Int,
    private val num2 : Int,
    private val num3 : Int
){
    //calculate profit
    fun calProfit() = (num1 * num2).toDouble()

    //calculate commission
    fun calCommssion() = calProfit() * ((num3.toDouble())/100).toDouble()

    //calculate totalProfit
    fun totalProfit() = (calProfit() - calCommssion()).toDouble()
}