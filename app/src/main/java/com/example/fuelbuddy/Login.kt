package com.example.fuelbuddy

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn: Button = findViewById(R.id.LoginBtn)
        val loginUsername :TextView = findViewById(R.id.edtLoginName)
        val loginPassword :TextView = findViewById(R.id.edtPassword)

        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {
            val email = loginUsername.text.toString()
            val password  = loginPassword.text.toString()

            if( email.isNotEmpty() && password.isNotEmpty() ) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            val intent = Intent(this, MainActivity::class.java)
//                            intent.putExtra("User", user)
                            startActivity(intent)
                            Toast.makeText(
                                baseContext,
                                "Successfully Signed in",
                                Toast.LENGTH_LONG,
                            ).show()
//                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
//                            updateUI(null)
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill the email and password", Toast.LENGTH_LONG).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("User", currentUser)
            startActivity(intent)
        }
    }

}