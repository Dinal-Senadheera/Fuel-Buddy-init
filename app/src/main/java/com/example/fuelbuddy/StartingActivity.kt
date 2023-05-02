package com.example.fuelbuddy

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class StartingActivity : AppCompatActivity() {
    private lateinit var splashScreenImg : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // on below line we are configuring
        // our window to full screen
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
        setContentView(R.layout.activity_start_app)

//        val handler = Handler(Looper.getMainLooper())
//
//        handler.postDelayed({
//            val i = Intent(
//                this@StartingActivity,
//                Login::class.java
//            )
//            startActivity(i)
//            finish()
//        }, 1000)

        splashScreenImg = findViewById(R.id.startLogoImg)

        splashScreenImg.alpha = 0f
        splashScreenImg.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(
                this@StartingActivity,
                Login::class.java
            )
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
    }
}