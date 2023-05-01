package com.example.fuelbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val loginBtn: Button = findViewById(R.id.LoginBtn)
        val loginUsername :TextView = findViewById(R.id.edtLoginName)
        val loginPassword :TextView = findViewById(R.id.edtPassword)

        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {
            val email = loginUsername.text.toString()
            val password  = loginPassword.text.toString()

            if( email.isNotEmpty() && password.isNotEmpty() ) {

            } else {
                Toast.makeText(this, "Please fill the email and password", Toast.LENGTH_LONG).show()
            }
        }
    }



}