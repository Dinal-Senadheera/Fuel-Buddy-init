package com.example.fuelbuddy

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.fuelbuddy.dataClasses.Posted
import com.example.fuelbuddy.dataClasses.vehicleList
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class vehicleEdit : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var vehicleNumber: TextInputLayout
    private lateinit var VehicleType: TextInputLayout
    private lateinit var chassis_Number: TextInputLayout
    private lateinit var pule_Type: TextInputLayout
    private lateinit var btn_vehi_update : Button
    private lateinit var btn_vehi_delete : Button
    private lateinit var userName: TextView
    
    
    var vehinum : String ?= null
    var vehiType : String ?= null
    var chassisNumber : String ?= null
    var puleType :String ? = null
    var postID : String ?= null
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
        chassis_Number = findViewById(R.id.chassisNumber)
        pule_Type = findViewById(R.id.puleType)
        btn_vehi_update = findViewById(R.id.btn_vehi_update)
        btn_vehi_delete = findViewById(R.id.btn_vehi_delete)

        val bundle: Bundle? = intent.extras
        vehinum = bundle?.getString("vehinum")
        vehiType = bundle?.getString("vehiType")
        chassisNumber = bundle?.getString("chassisNumber")
        puleType = bundle?.getString("puleType")
        postID = bundle?.getString("PostID")

        vehicleNumber.editText?.setText(vehinum.toString())
        VehicleType.editText?.setText(vehiType.toString())
        chassis_Number.editText?.setText(chassisNumber.toString())
        pule_Type.editText?.setText(puleType.toString())



        btn_vehi_update.setOnClickListener {
            //calling editPost function
            updateVehicle()
        }

    }

    private fun updateVehicle() {
        val post = postID.toString()
        val uID: String = auth.currentUser?.uid.toString()
        val vNumber = vehicleNumber.editText?.text.toString()
        val vType = VehicleType.editText?.text.toString()
        val cNumber = chassis_Number.editText?.text.toString()
        val pType = pule_Type.editText?.text.toString()

        val postDet = vehicleList(uID , vNumber ,vType , cNumber,pType)
        Log.d(ContentValues.TAG,postDet.toString())



    }

}