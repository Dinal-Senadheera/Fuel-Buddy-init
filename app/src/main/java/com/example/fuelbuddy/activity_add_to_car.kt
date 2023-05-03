package com.example.fuelbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.example.fuelbuddy.dataClasses.vehicleList
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class activity_add_to_car : AppCompatActivity() {
    private lateinit var vehicleNumber: TextInputLayout
    private lateinit var VehicleType: TextInputLayout
    private lateinit var chassisNumber: TextInputLayout
    private lateinit var puleType: TextInputLayout
    private lateinit var vehiShu: Button
    private lateinit var userName: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference
    private var uid: String? =null
    private var name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_car)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("vehicle")
        val user = auth.currentUser

        user?.let {
            name = it.displayName
            uid = it.uid
        }

        vehicleNumber = findViewById(R.id.vehinum)
        VehicleType = findViewById(R.id.vehiType)
        chassisNumber = findViewById(R.id.chassisNumber)
        puleType = findViewById(R.id.puleType)
        vehiShu = findViewById(R.id.vehiShu)
        userName = findViewById(R.id.userName)
        userName.text = "$name"




    }
    private fun savePosts() {

        val uniqueID: String = UUID.randomUUID().toString()
        val userID: String = auth.currentUser?.uid.toString()
        val vehinum = vehicleNumber.editText?.text.toString()
        val vehicleList = vehicleList(userID ,vehinum , vehiType , chassisNumber,puleType)

        database.child(uniqueID).setValue(vehicleList).addOnCompleteListener{
            Toast.makeText(this, "Post added Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Error" , Toast.LENGTH_LONG).show()
        }
    }
}