package com.example.fuelbuddy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.fuelbuddy.dataClasses.Posted
import com.example.fuelbuddy.dataClasses.vehicleList
import com.example.fuelbuddy.fragments.vehicleFragment
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
    var vehicleID : String ?= null
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
        vehicleID = bundle?.getString("vehicleID")

        vehicleNumber.editText?.setText(vehinum.toString())
        VehicleType.editText?.setText(vehiType.toString())
        chassis_Number.editText?.setText(chassisNumber.toString())
        pule_Type.editText?.setText(puleType.toString())



        btn_vehi_update.setOnClickListener {
            //calling editPost function
            updateVehicle()
        }

        btn_vehi_delete.setOnClickListener {
            //calling deletePost function
            deleteVehicle()
        }

    }

    private fun deleteVehicle() {
        val post = vehicleID.toString()       //assign post ID to post variable

        //remove post from firebase realtime database
        database.child(post).removeValue()


            //call addOnSuccessListener if vehicle deleted successfully
            .addOnSuccessListener {
                Toast.makeText(this, "Vehicle Deleted Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this , MainActivity::class.java)
                finish()
                startActivity(intent)
            }.addOnFailureListener {    //call addOnFailureListener if vehicle deletion failed
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
    }

    private fun updateVehicle() {
        val post = vehicleID.toString()
        val uID: String = auth.currentUser?.uid.toString()
        val vNumber = vehicleNumber.editText?.text.toString()
        val vType = VehicleType.editText?.text.toString()
        val cNumber = chassis_Number.editText?.text.toString()
        val pType = pule_Type.editText?.text.toString()

        val vehidata = vehicleList(uID , vNumber ,vType , cNumber,pType)
        Log.d(TAG,vehidata.toString())

        database.child(post).setValue(vehidata)
            .addOnCompleteListener{//call addOnSuccessListener if vehicle updated successfully
                //display short time notification in this activity
                //LENGTH_SHORT , LENGTH_LONG ---> display time duration of toast
                Toast.makeText(this, "Vehicle Updated Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this , MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {//call addOnFailureListener if post deletion failed
                Toast.makeText(this,"Error" , Toast.LENGTH_LONG).show()
            }



    }

}