package com.example.fuelbuddy

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.fuelbuddy.dataClasses.Posted
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MenuInstrumentedTest {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref: DatabaseReference
    private var name:String ?= null
    private var uid:String ?= null
    private var formattedTotal:String ?= null



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
        uid = auth.currentUser?.uid
        dbref = FirebaseDatabase.getInstance().getReference("Posts")
        val posts : Query = dbref.orderByChild("userID").equalTo(uid)
        posts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var total = 0
                    for (postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(Posted::class.java)
                        total += ((post?.Qty?.times(post.UnitProfit!!)!!))
                    }
                    val calculations = Calculations(total, 99,100)
                    formattedTotal = String.format("%.2f",calculations.doubleMultiplication())
                    formattedTotal = "Rs. ".plus(formattedTotal)
                }

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    @Test
    fun testName(){
        onView(withId(R.id.Username)).check(matches(withText("Hello $name")))
    }

    @Test
    fun testTotal() {
        onView(withId(R.id.edtProfit)).check(matches(withText(formattedTotal)))
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }

}