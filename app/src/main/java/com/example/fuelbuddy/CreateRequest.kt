package com.example.fuelbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fuelbuddy.dataClasses.Request
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

private lateinit var auth: FirebaseAuth
private var name:String ?= null
private var uid:String ?= null

class CreateRequest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if( user == null) {
            startActivity(Intent(this, Login::class.java))
        }
        user?.let {
            name = it.displayName
            uid = it.uid
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_request)

        val dbref = FirebaseDatabase.getInstance().getReference("Requests")
        val qty :TextView = findViewById(R.id.edtQty)
        val description: TextView = findViewById(R.id.edtDescriptionBuy)
        val confirmBtn: Button = findViewById(R.id.edtConfirm)
        val displayPrice :TextView = findViewById(R.id.price)
        val displayQty : TextView = findViewById(R.id.edtQuantity)

        val bundle:Bundle ?= intent.extras
        val postUserID = bundle?.getString("postUserID")
        val post = bundle?.getString("Post")
        val price = bundle?.getInt("Price")
        val availableQty = bundle?.getInt("qty")

        displayPrice.text = price.toString()
        displayQty.text = availableQty.toString()



        confirmBtn.setOnClickListener {

            val quantity = qty.text.toString()
            val des = description.text.toString()
            if (quantity.isNotEmpty()) {
                if (quantity.toInt() <= availableQty!!) {
                    val calculation = Calculations(quantity.toInt(), price!!.toInt(), 1)
                    val formattedTotal =
                        String.format("%.2f", calculation.toDoubleMultiplication())
                    val builder = AlertDialog.Builder(this)
                    val message = "Do you want to send a request to purchase fuel?"
                    builder.setTitle("Your total profit will be Rs".plus(formattedTotal))
                    builder.setMessage(message)
                    builder.setPositiveButton("Yes") { _, _ ->
                        val uniqueID: String = UUID.randomUUID().toString()
                        val request =
                            Request(name, quantity.toInt(), des, post, postUserID, uid)

                        dbref.child(uniqueID).setValue(request).addOnCompleteListener {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(
                                this,
                                "Request added Successfully",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                    builder.setNegativeButton("Cancel"){_, _ ->
                    }
                    val dialog = builder.create()
                    dialog.show()
                }else {
                    Toast.makeText(
                        this,
                        "Request quantity cannot exceed post quantity",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Please input the quantity", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
