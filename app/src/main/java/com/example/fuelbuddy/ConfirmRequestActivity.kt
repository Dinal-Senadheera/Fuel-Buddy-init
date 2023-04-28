package com.example.fuelbuddy

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import java.lang.Integer.parseInt

class ConfirmRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_request)

        val dbref = FirebaseDatabase.getInstance().getReference("Posts")
        val nameRequest:TextView = findViewById(R.id.edtPersonName)
        val qtyRequest:TextView = findViewById(R.id.edtQuantity)
        val descriptionRequest:TextView = findViewById(R.id.edtDescription)
        val confirmBtn:Button = findViewById(R.id.edtConfirm)

        val bundle:Bundle ?= intent.extras
        val name = bundle!!.getString("Name")
        val qty = bundle!!.getInt("Qty")
        val des = bundle!!.getString("Description")
        val postID = bundle!!.getString("PostID")

        nameRequest.text = name
        qtyRequest.text = qty.toString()
        descriptionRequest.text = des

        confirmBtn.setOnClickListener {
            dbref.child(postID!!).child("Qty").get().addOnSuccessListener {
                if(parseInt(it.value.toString()) - qty < 0) {
                    val builder = AlertDialog.Builder(this)
                    val message = "Do you want to Allocate the remaining Quantity(${it.value.toString()}L) to this user?"
                    builder.setTitle("Current Quantity exceeds requested value")
                    builder.setMessage(message)
                    builder.setPositiveButton("Yes") { _, _ ->
                        Toast.makeText(this, "Request Accepted", Toast.LENGTH_LONG).show()
                    }
                    builder.setNegativeButton("Cancel"){_, _ ->
                    }
                    val dialog = builder.create()
                    dialog.show()

                } else {
                    dbref
                        .child(postID!!)
                        .child("Qty")
                        .setValue(ServerValue.increment(-qty.toDouble()))
                    Toast.makeText(this, "Request Accepted", Toast.LENGTH_LONG).show()
                }
            }

        }

    }

}
