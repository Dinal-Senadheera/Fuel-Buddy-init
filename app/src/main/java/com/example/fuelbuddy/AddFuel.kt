package com.example.fuelbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fuelbuddy.dataClasses.Posted
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class AddFuel : AppCompatActivity() {
    //private lateinit var binding : AddFuelBinding
    private lateinit var database : DatabaseReference
    private lateinit var fuelType : EditText
    private lateinit var quantity : EditText
    private lateinit var profit : EditText
    private lateinit var submit : Button
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = AddFuelBinding.inflate(layoutInflater)
        setContentView(R.layout.add_fuel)

        fuelType = findViewById(R.id.fuelType)
        quantity = findViewById(R.id.qtyInput)
        profit = findViewById(R.id.edt_unitPrice)
        submit = findViewById(R.id.btnSubmit)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Posts")

        submit.setOnClickListener{
            savePosts()
            val intent = Intent(this, ConfirmPost::class.java)
            startActivity(intent)
        }

    }

    private fun savePosts() {
        //getting values
        val Type = fuelType.text.toString()
        val Qty = quantity.text.toString().toInt()
        val UnitProfit = profit.text.toString().toInt()

//        if (Type.isEmpty()){
//             fuelType.error = "Please enter fuel type."
//        }
//        if (Qty.isEmpty()){
//            quantity.error = "Please enter quantity."
//        }
//        if (UnitProfit.isEmpty()){
//            profit.error = "Please enter unit price."
//        }
        val uniqueID: String = UUID.randomUUID().toString()
        val userID: String = auth.currentUser?.uid.toString()

        database.push().key!!

        val post = Posted(userID , Type , Qty , UnitProfit)

        database.child(uniqueID).setValue(post).addOnCompleteListener{
            Toast.makeText(this, "Post added Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Error" , Toast.LENGTH_LONG).show()
        }
    }
}