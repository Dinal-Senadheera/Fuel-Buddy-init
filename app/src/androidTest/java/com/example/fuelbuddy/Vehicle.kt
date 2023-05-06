package com.example.fuelbuddy

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.textfield.TextInputLayout
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class Vehicle {
    private lateinit var appContext : Context

    @Test
    fun useAppContext(){
        //Context of the app under test

        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.fuelbuddy",appContext.packageName)
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(activity_add_to_car::class.java)
    private lateinit var activityScenario: ActivityScenario<activity_add_to_car>

    @Before
    fun setUp(){
        activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity{ activity ->
            activity.findViewById<TextInputLayout>(R.id.vehinum).editText?.setText("")
            activity.findViewById<TextInputLayout>(R.id.vehiType).editText?.setText("")
            activity.findViewById<TextInputLayout>(R.id.chassisNumber).editText?.setText("")
            activity.findViewById<TextInputLayout>(R.id.puleType).editText?.setText("")


        }
    }

    @Test
    fun testForm(){
        onView(withId(R.id.vehiShu)).perform(click())
        onView(withId(R.id.vehicleTopic)).check(matches(withText(R.string.Add_vehiclesTopic)))
    }


}