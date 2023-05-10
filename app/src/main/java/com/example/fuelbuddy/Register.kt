package com.example.fuelbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fuelbuddy.dataClasses.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // set widgets
        val registerBtn: Button = findViewById(R.id.LoginBtn)
        val registerName :TextView = findViewById(R.id.edtUserName)
        val registerEmail :TextView = findViewById(R.id.edtLoginName)
        val registerPassword :TextView = findViewById(R.id.edtPassword)
        val registerRePassword :TextView = findViewById(R.id.edtRePassword)
        val registerNIC :TextView = findViewById(R.id.edtNIC)
        val registerContactNumber :TextView = findViewById(R.id.edtPhone)
        val loginLink : TextView = findViewById(R.id.SignUpLink)

        // initialize databases
        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("Users")


        registerBtn.setOnClickListener {
            // get input dta
            val name = registerName.text.toString()
            val email = registerEmail.text.toString()
            val password  = registerPassword.text.toString()
            val rePassword = registerRePassword.text.toString()
            val nic = registerNIC.text.toString()
            val phone = registerContactNumber.text.toString()

            // validations
            if( name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() &&
                rePassword.isNotEmpty() && nic.isNotEmpty() && phone.isNotEmpty()) {
                // validations
                if( password == rePassword){
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {

                                val user = auth.currentUser
                                // save basic information in the auth server
                                val profileUpdates = userProfileChangeRequest {
                                    displayName = name
                                }

                                user!!.updateProfile(profileUpdates)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            // save additional information in real time database
                                            val registeredUser = User(nic,phone)
                                            dbref.child(user.uid).setValue(registeredUser).addOnCompleteListener{
                                                val intent = Intent(this,MainActivity::class.java)
                                                startActivity(intent)
                                                Toast.makeText(this, "User added Successfully", Toast.LENGTH_LONG).show()
                                            }.addOnFailureListener {
                                                Toast.makeText(this,"Error" , Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(
                                    baseContext,
                                    task.exception?.message,
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                } else {
                    // validation toast
                    Toast.makeText(this, "Password and confirm password is not the same", Toast.LENGTH_SHORT).show()
                }
            } else {
                // validation toast
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        loginLink.setOnClickListener {
            // navigate to Login activity
            startActivity(Intent(this,Login::class.java))
        }
    }



}