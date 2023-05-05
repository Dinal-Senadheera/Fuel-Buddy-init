package com.example.fuelbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fuelbuddy.dataClasses.Posted
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ConfirmPost : AppCompatActivity() {
    //initialization
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var fuelType : TextView
    private lateinit var quantity : TextView
    private lateinit var unitPrice : TextView
    private lateinit var totalProfit : TextView
    private lateinit var confirm : Button
    ////declare variables and initialize to null
    var Type : String ?= null
    var Qty : Int ?= null
    var UnitProfit : Int ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_sell)

        //obtain an instance of posted class
        auth = FirebaseAuth.getInstance()
        //call current user to get firebase object
        database = FirebaseDatabase.getInstance().getReference("Posts")

        //reference to UI components
        fuelType = findViewById(R.id.type)
        quantity = findViewById(R.id.qtyL)
        unitPrice = findViewById(R.id.unitPrice)
        totalProfit = findViewById(R.id.totProfit)
        confirm = findViewById(R.id.btnConfirm)

        //access fuel type , quantity , unitprofit passed along with intent and initialize
        val bundle:Bundle ?= intent.extras
        Type = bundle?.getString("Type")
        Qty = bundle?.getInt("Qty")
        UnitProfit = bundle?.getInt("UnitProfit")

        //set the text of EditText
        fuelType.text = Type.toString()
        quantity.text = Qty.toString()
        unitPrice.text = "Rs.".plus(UnitProfit.toString()).plus(".00")

//        Log.d(TAG, fuelType.text.toString())
//        Log.d(TAG, quantity.text.toString())
//        Log.d(TAG, unitPrice.text.toString())

        //calculate total profit
        var profit = Qty!! * UnitProfit!!
        val calculation = Calculations(profit , 99 , 100)
        var formattedProfit = String.format("%.2f" , calculation.doubleMultiplication())
        totalProfit.text = "Rs".plus(formattedProfit)
//        Log.d(TAG, totalProfit.text.toString())

        confirm.setOnClickListener{
            savePosts() //call savepost function
        }

    }

    private fun savePosts() {

        val uniqueID: String = UUID.randomUUID().toString()     //create a unique random number
        val userID: String = auth.currentUser?.uid.toString()   //get current user ID
        val post = Posted(userID , Type , Qty , UnitProfit)

        database.child(uniqueID).setValue(post).addOnCompleteListener{
            //display short time notification in this activity
            //LENGTH_SHORT , LENGTH_LONG ---> display time duration of toast
            Toast.makeText(this, "Post added Successfully", Toast.LENGTH_SHORT).show()
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this,"Error" , Toast.LENGTH_LONG).show()
            var intent = Intent(this,AddFuel::class.java)
            startActivity(intent)
        }
    }

}