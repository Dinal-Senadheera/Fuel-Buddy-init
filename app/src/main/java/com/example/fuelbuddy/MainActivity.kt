package com.example.fuelbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val fragmentPosted = PostedFragment()
    private val requestFragment = RequestFragment()
    private lateinit var requestBtn: Button
    private lateinit var postBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestBtn = findViewById(R.id.edtRequests)
        postBtn = findViewById(R.id.edtPosted)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.postedInit, fragmentPosted)
            commit()
        }

        requestBtn.setOnClickListener {
            requestBtn.setBackgroundColor(resources.getColor(R.color.primary))
            postBtn.setBackgroundColor(resources.getColor(R.color.background))
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.postedInit, requestFragment)
                commit()
            }
        }

        postBtn.setOnClickListener {
            postBtn.setBackgroundColor(resources.getColor(R.color.primary))
            requestBtn.setBackgroundColor(resources.getColor(R.color.background))
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.postedInit, fragmentPosted)
                commit()
            }
        }

    }



}