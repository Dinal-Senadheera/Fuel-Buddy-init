package com.example.fuelbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class AddFuel : AppCompatActivity() {
    //initialize
    private lateinit var fuelType : EditText
    private lateinit var quantity : EditText
    private lateinit var profit : EditText
    private lateinit var submit : Button
    private lateinit var userName: TextView
    private lateinit var auth : FirebaseAuth
    private var name:String ?= null

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

        submit.setOnClickListener{

            //launch new activity
            val intent = Intent(this, ConfirmPost::class.java)

            //add extra details(fuel type , quantity , profit) to intent
            intent.putExtra("Type", fuelType.text.toString())
            intent.putExtra("Qty" , quantity.text.toString().toInt())
            intent.putExtra("UnitProfit" , profit.text.toString().toInt())
//            Log.d(TAG, fuelType.toString())
//            Log.d(TAG, quantity.toString())
//            Log.d(TAG, profit.toString())

            //
            startActivity(intent)
        }
    //intent.put extra(key values)
    }
}