package com.example.fuelbuddy

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.auth.FirebaseAuth
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
class MenuInstrumentedTest {
    private lateinit var auth: FirebaseAuth
    private var name:String ?= null

    private lateinit var appContext: Context
    @Test
    fun useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.fuelbuddy", appContext.packageName)
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var activityScenario: ActivityScenario<MainActivity>


    @Before
    fun setUp() {
        activityScenario = activityScenarioRule.scenario
        auth = FirebaseAuth.getInstance()
        name = auth.currentUser?.displayName
    }

    @Test
    fun testName(){
        onView(withId(R.id.Username)).check(matches(withText("Hello $name")))
    }

    @Test
    fun testRecyclerView() {
        onView(withId(R.id.edtRequests)).perform(click())
        
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }

}