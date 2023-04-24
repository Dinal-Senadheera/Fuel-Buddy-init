package com.example.fuelbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val fragmentMenu = MenuFragment()
    private lateinit var mainBottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment, fragmentMenu)
            commit()
        }
        mainBottomNav = findViewById(R.id.mainBottomNav)
        mainBottomNav.setOnItemSelectedListener { item -> when (item.itemId) {
            R.id.bottom_menu_text -> { replaceFragment(fragmentMenu)
                                        true }
//            R.id.bottom_home_text -> { replaceFragment(blogFragment)
//                                        true }
//            R.id.bottom_vehicle_text -> { replaceFragment(accountFragment)
//                                            true }
            else -> false } }

    }

    private fun replaceFragment(fragment :Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment, fragment)
            commit()
        }
    }



}