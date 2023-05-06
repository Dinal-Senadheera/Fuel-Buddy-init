package com.example.fuelbuddy

import android.content.Context
import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import org.jetbrains.annotations.TestOnly
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostInstumentedTest {
    private lateinit var appContext : Context

    @Test
    fun useAppContext(){
        //Context of the app under test
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.fuelbuddy", appContext.packageName)
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(AddFuel::class.java)
    private lateinit var activityScenario : ActivityScenario<AddFuel>

    @Before
    fun setUp(){
        activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity { activity ->
            activity.findViewById<EditText>(R.id.fuelType).setText("")
            activity.findViewById<EditText>(R.id.qtyInput).setText("")
            activity.findViewById<EditText>(R.id.edt_unitPrice).setText("")
        }


        }

    @Test
    fun testForm(){
        onView(withId(R.id.btnSubmit)).perform(click())
        onView(withId(R.id.add_fuel)).check(matches(withText(R.string.page_title)))
    }

}