package com.example.fuelbuddy

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.lang.Boolean.TRUE

class AddFuel : AppCompatActivity() {
    //initialize
    private lateinit var fuelType : EditText
    private lateinit var quantity : EditText
    private lateinit var profit : EditText
    private lateinit var submit : Button
    private lateinit var userName: TextView
    private lateinit var auth : FirebaseAuth
    private var name:String ?= null
    var ftype1 = "petrol"
    var ftype2 = "diesel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_fuel)

        //obtain an instance of posted class
        auth = FirebaseAuth.getInstance()
        //call current user to get firebase object
        val user = auth.currentUser

        //check if user is not null
        user?.let {
            name = it.displayName //assign display name to name variable
        }

        //reference to UI components
        fuelType = findViewById(R.id.fuelType)
        quantity = findViewById(R.id.qtyInput)
        profit = findViewById(R.id.edt_unitPrice)
        submit = findViewById(R.id.btnSubmit)
        userName = findViewById(R.id.userName)
        //display current user name
        userName.text = "$name"

        //perform action when add fuel button is pressed
        submit.setOnClickListener{
            val fType = fuelType.text.toString()
            val fqty =  quantity.text.toString()
            val fprofit = profit.text.toString()

            //launch new activity
            val intent = Intent(this, ConfirmPost::class.java)


            //validate and add extra details(fuel type , quantity , profit) to intent
            if(fType.isEmpty()){
                Toast.makeText(this, "Please input fuel Type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(!(fType.lowercase() == ftype1 || fType.lowercase()==ftype2)){
                Toast.makeText(this, "Fuel Type is invalid", Toast.LENGTH_SHORT).show()
                Log.d(TAG,(fType.compareTo(ftype1)).toString())
                return@setOnClickListener
            }else{
                intent.putExtra("Type", fType)
            }

            if(fqty.isEmpty()){
                Toast.makeText(this, "Please input quantity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                intent.putExtra("Qty", fqty.toInt())
            }

            if(fprofit.isEmpty()){
                Toast.makeText(this, "Please input unit profit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                intent.putExtra("UnitProfit" , fprofit.toInt())
            }

//            Log.d(TAG, fuelType.toString())
//            Log.d(TAG, quantity.toString())
//            Log.d(TAG, profit.toString())

            //
            startActivity(intent)
        }
    }
}