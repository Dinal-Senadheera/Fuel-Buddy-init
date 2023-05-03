package com.example.fuelbuddy

import android.content.Context
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var decorView: View
    private lateinit var appContext: Context
    @Test
    fun useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.fuelbuddy", appContext.packageName)
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
//    val activityTestRule = ActivityTestRule(CreateRequest::class.java)
    private lateinit var activityScenario: ActivityScenario<MainActivity>


    @Before
    fun setUp() {
        activityScenario = activityScenarioRule.scenario
    }

    @Test
    fun testName(){
        onView(withId(R.id.Username)).check(matches(withText("Hello Dinal S")))
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }

}