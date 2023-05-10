package com.example.fuelbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class Account: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var userName : TextView
    private lateinit var userEmail : TextView
    private lateinit var btnUpdate : Button
    private var name:String ?= null
    private var email:String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_details)
        // see if the user is logged in
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if( user == null) {
            startActivity(Intent(this, Login::class.java))
        }
        // get user name and email
        user?.let {
            name = it.displayName
            email = it.email
        }

        // set relevant widgets
        userName = findViewById(R.id.edtDisplayName)
        userEmail = findViewById(R.id.edtEmail)
        btnUpdate = findViewById(R.id.btnUpdate)

        //display name and email
        userName.text = name.toString()
        userEmail.text = email.toString()

        // execute when btnUpdate is clicked
        btnUpdate.setOnClickListener {
            // validations
            if(userName.text.isNotEmpty() && userEmail.text.isNotEmpty()) {
                val user = auth.currentUser

                // profile Update
                val profileUpdates = userProfileChangeRequest {
                    displayName = userName.text.toString()
                    email = userEmail.text.toString()
                }

                // update profile if the user is not not null
                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // navigate to MainActivity if updated
                            startActivity(Intent(this, MainActivity::class.java))
                            Toast.makeText(this, "Successfully Updated", Toast.LENGTH_LONG).show()
                        } else {
                            // show error message if something goes wrong
                            Toast.makeText(
                                baseContext,
                                task.exception?.message,
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }

        }
    }
}