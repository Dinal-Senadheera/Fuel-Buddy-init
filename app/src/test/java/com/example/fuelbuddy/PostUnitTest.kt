package com.example.fuelbuddy

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PostUnitTest{
    val profit = calculateProfit(10 , 50 , 1)

    @Test
    fun calProfit_isCorrect(){
        assertEquals(500.0, profit.calProfit())
    }

    @Test
    fun calCommission_isCorrect(){
        assertEquals(5.0, profit.calCommssion())
    }

    @Test
    fun calTotalProfit_isCorrect(){
        assertEquals(495.0 , profit.totalProfit())
    }
}