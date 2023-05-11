package com.example.fuelbuddy

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.lang.Integer.parseInt

class ConfirmRequestActivity : AppCompatActivity() {
    private var postedType:String ?= null
    private var postedQty:String ?= null
    private var postedProfit:String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_request)

        // initialize database references with correct paths
        val dbrefPosts = FirebaseDatabase.getInstance().getReference("Posts")
        val dbrefReq = FirebaseDatabase.getInstance().getReference("Requests")

        // set widgets
        val nameRequest:TextView = findViewById(R.id.edtPersonName)
        val qtyRequest:TextView = findViewById(R.id.edtQuantity)
        val descriptionRequest:TextView = findViewById(R.id.edtDescription)
        val confirmBtn:Button = findViewById(R.id.edtConfirm)
        val declineButton:Button = findViewById(R.id.edtDecline)
        val postType:TextView = findViewById(R.id.edtReqType)
        val postQty:TextView = findViewById(R.id.edtReqQTY)
        val postUnitProfit:TextView = findViewById(R.id.edtReqUnitProfit)

        // get the data passed through intents
        val bundle:Bundle ?= intent.extras
        val name = bundle?.getString("Name")
        val qty = bundle?.getInt("Qty")
        val des = bundle?.getString("Description")
        val postID = bundle?.getString("PostID")
        val reqID = bundle?.getString("ReqID")

        // get the type, qty and unitProfit of the post where the request was created for and display
        dbrefPosts.child(postID!!).child("type").get().addOnSuccessListener {
            postedType = it.value.toString()
            postType.text = postedType
        }
        dbrefPosts.child(postID).child("qty").get().addOnSuccessListener {
            postedQty = it.value.toString()
            postQty.text = postedQty.plus("L")

        }
        dbrefPosts.child(postID).child("unitProfit").get().addOnSuccessListener {
            postedProfit = it.value.toString()
            val formattedPrice = String.format("%.2f", postedProfit!!.toDouble())
            postUnitProfit.text = formattedPrice
        }

        // display request data
        nameRequest.text = name
        qtyRequest.text = qty.toString().plus("L")
        // set custom message if the user did not give a description
        if (des?.length != 0) {
            descriptionRequest.text = des
        } else {
            "User did not give a description".also { descriptionRequest.text = it }
        }


        // execute on confirmBtn click
        confirmBtn.setOnClickListener {
            dbrefPosts.child(postID).child("qty").get().addOnSuccessListener {
                // check if the requested amount satisfies the post
                if(parseInt(it.value.toString()) - qty!! <= 0) {
                    val builder = AlertDialog.Builder(this)
                    val message = "Do you want to Allocate the remaining Quantity(${it.value.toString()}L) to this user?"

                    // set Title on the requested amount
                    if( parseInt(it.value.toString()) - qty < 0 ) {
                        builder.setTitle("Current Quantity exceeds requested value")
                    } else {
                        builder.setTitle("Post quantity satisfied. The post will be closed..")
                    }

                    builder.setMessage(message)
                    builder.setPositiveButton("Yes") { _, _ ->
                        Toast.makeText(this, "Request Accepted", Toast.LENGTH_LONG).show()
                        // remove the entries from the database
                        dbrefReq.child(reqID!!).removeValue()
                        dbrefPosts.child(postID).removeValue()

                        //Delete all related requests since the post is completed
                        val reqQuery : Query = dbrefReq.orderByChild("post").equalTo(postID)
                        reqQuery.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                for (postSnapshot in dataSnapshot.children) {
                                    postSnapshot.ref.removeValue().addOnSuccessListener {
                                        // navigate to MainActivity on success
                                        val intent = Intent(this@ConfirmRequestActivity,MainActivity::class.java)
                                        startActivity(intent)
                                    }.addOnFailureListener {
                                        Toast.makeText(this@ConfirmRequestActivity, "Could not update database", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Getting Post failed, log a message
                                Log.w(TAG, "loadReqQuery:onCancelled", databaseError.toException())
                            }
                        })
                    }
                    builder.setNegativeButton("Cancel"){_, _ ->
                    }
                    val dialog = builder.create()
                    dialog.show()

                } else {
                    // Update quantity if the requested quantity does not exceed current quantity
                    dbrefPosts
                        .child(postID)
                        .child("qty")
                        .setValue(ServerValue.increment(-qty.toDouble()))
                    dbrefReq.child(reqID!!).removeValue().addOnSuccessListener {
                        val intent = Intent(this@ConfirmRequestActivity,MainActivity::class.java)
                        startActivity(intent)
                    }.addOnFailureListener {
                        Toast.makeText(this@ConfirmRequestActivity, "Could not update database", Toast.LENGTH_LONG).show()
                    }
                    Toast.makeText(this, "Request Accepted", Toast.LENGTH_LONG).show()
                }
            }


        }

        // Delete the request if it is declined
        declineButton.setOnClickListener {
            dbrefReq.child(reqID!!).removeValue()
                .addOnSuccessListener {
                    val intent = Intent(this@ConfirmRequestActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                    Toast.makeText(this, "Request Declined", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this@ConfirmRequestActivity, "Could not update database", Toast.LENGTH_LONG).show()
            }

        }

    }

}
