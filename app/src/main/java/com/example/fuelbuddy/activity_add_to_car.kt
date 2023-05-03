package com.example.fuelbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class activity_add_to_car : AppCompatActivity() {
    private lateinit var vehicleNumber: EditText
    private lateinit var VehicleType: EditText
    private lateinit var chassisNumber: EditText
    private lateinit var puleType: EditText
    private lateinit var vehiShu: Button
    private lateinit var userName: TextView
    private lateinit var auth: FirebaseAuth
    private var name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_car)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        user?.let {
            name = it.displayName
        }

        vehicleNumber = findViewById(R.id.vehicleNumber)
        VehicleType = findViewById(R.id.VehicleType)
        chassisNumber = findViewById(R.id.chassisNumber)
        puleType = findViewById(R.id.puleType)
        vehiShu = findViewById(R.id.vehiShu)
        userName = findViewById(R.id.userName)
        userName.text = "$name"


    }
}