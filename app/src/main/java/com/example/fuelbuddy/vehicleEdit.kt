package com.example.fuelbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class vehicleEdit : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var vehicleNumber: TextInputLayout
    private lateinit var VehicleType: TextInputLayout
   // private lateinit var chassisNumber: TextInputLayout
   // private lateinit var puleType: TextInputLayout
    private lateinit var btn_vehi_update : Button
    //private lateinit var btn_vehi_delete : Button
    private lateinit var userName: TextView
    var vehinum : String ?= null
    var vehiType : String ?= null
    var chassisNumber : String ?= null
    var puleType :String ? = null
    private var name:String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_edit)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        user?.let {
            name = it.displayName
        }

        database = FirebaseDatabase.getInstance().getReference("vehicle")
        userName = findViewById(R.id.userName)
        userName.text = "$name"

        vehicleNumber = findViewById(R.id.vehinum)
        VehicleType = findViewById(R.id.vehiType)
       // chassisNumber = findViewById(R.id.chassisNumber)
       // puleType = findViewById(R.id.puleType)
        btn_vehi_update = findViewById(R.id.btn_vehi_update)
        //btn_vehi_delete = findViewById(R.id.btn_vehi_delete)

        val bundle: Bundle? = intent.extras
        vehinum = bundle?.getString("vehinum")
        vehiType = bundle?.getString("vehiType")
        chassisNumber = bundle?.getString("chassisNumber")
        puleType = bundle?.getString("puleType")

        btn_vehi_update.setOnClickListener{
            updateVehicle()

            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateVehicle() {
        val vehicleNumber = vehicleNumber.editText.toString()
        val VehicleType = VehicleType.editText.toString()
       // val chassisNumber = chassisNumber.editText.toString()
        //val puleType = puleType.editText.toString()




    }

}