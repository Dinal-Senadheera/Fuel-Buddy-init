package com.example.fuelbuddy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class StartingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // on below line we are configuring
        // our window to full screen
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
        setContentView(R.layout.activity_start_app)

        Handler().postDelayed({
            val i = Intent(
                this@StartingActivity,
                MainActivity::class.java
            )
            startActivity(i)
            finish()
        }, 2000)
    }
}