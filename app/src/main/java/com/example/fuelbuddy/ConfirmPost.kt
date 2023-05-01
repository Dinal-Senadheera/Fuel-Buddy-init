package com.example.fuelbuddy

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
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var fuelType : TextView
    private lateinit var quantity : TextView
    private lateinit var unitPrice : TextView
    private lateinit var totalProfit : TextView
    private lateinit var confirm : Button
    var Type : String ?= null
    var Qty : Int ?= null
    var UnitProfit : Int ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_sell)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Posts")

        fuelType = findViewById(R.id.type)
        quantity = findViewById(R.id.qtyL)
        unitPrice = findViewById(R.id.unitPrice)
        totalProfit = findViewById(R.id.totProfit)
        confirm = findViewById(R.id.btnConfirm)

        val bundle:Bundle ?= intent.extras
        Type = bundle?.getString("Type")
        Qty = bundle?.getInt("Qty")
        UnitProfit = bundle?.getInt("UnitProfit")

        fuelType.text = Type.toString()
        quantity.text = Qty.toString()
        unitPrice.text = UnitProfit.toString()

//        Log.d(TAG, fuelType.text.toString())
//        Log.d(TAG, quantity.text.toString())
//        Log.d(TAG, unitPrice.text.toString())

        totalProfit.text = ((Qty!! * UnitProfit!!).toDouble() * (0.99)).toString()
//        Log.d(TAG, totalProfit.text.toString())

        confirm.setOnClickListener{
            savePosts()
        }

    }

    private fun savePosts() {

        val uniqueID: String = UUID.randomUUID().toString()
        val userID: String = auth.currentUser?.uid.toString()
        val post = Posted(userID , Type , Qty , UnitProfit)

        database.child(uniqueID).setValue(post).addOnCompleteListener{
            Toast.makeText(this, "Post added Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Error" , Toast.LENGTH_LONG).show()
        }
    }

}