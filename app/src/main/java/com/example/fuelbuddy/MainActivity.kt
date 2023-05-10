package com.example.fuelbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fuelbuddy.fragments.AllPostsFragment
import com.example.fuelbuddy.fragments.MenuFragment
import com.example.fuelbuddy.fragments.vehicleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val fragmentMenu = MenuFragment()
    private val fragmentAllPosts = AllPostsFragment()
    private val fragmentVehicle = vehicleFragment()
    private lateinit var mainBottomNav: BottomNavigationView
    private lateinit var mainTopNav : androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // default fragment
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment, fragmentMenu)
            commit()
        }

        // set widget
        mainBottomNav = findViewById(R.id.mainBottomNav)

        // begin transaction when a icon is clicked
        mainBottomNav.setOnItemSelectedListener { item -> when (item.itemId) {
            R.id.bottom_menu_text -> { replaceFragment(fragmentMenu)
                                        true }
            R.id.bottom_home_text -> { replaceFragment(fragmentAllPosts)
                                       true }
            R.id.bottom_vehicle_text -> { replaceFragment(fragmentVehicle)
                                           true }
            else -> false } }

        // set widget
        mainTopNav = findViewById(R.id.mainTopNav)

        // redirect when a text is clicked
        mainTopNav.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.top_account_text -> { startActivity((Intent(this,Account::class.java)))
                                            true}
                R.id.top_logout_text -> { signOut()
                                            true}
                else -> false
            }
        }

    }

    // function to signOut from the app
    private fun signOut() {
        Firebase.auth.signOut()
        startActivity(Intent(this,Login::class.java))
    }

    // function to replace one fragment with another
    private fun replaceFragment(fragment :Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment, fragment)
            commit()
        }
    }



}