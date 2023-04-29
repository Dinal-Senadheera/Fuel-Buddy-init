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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_request)

        val dbrefPosts = FirebaseDatabase.getInstance().getReference("Posts")
        val dbrefReq = FirebaseDatabase.getInstance().getReference("Requests")
        val nameRequest:TextView = findViewById(R.id.edtPersonName)
        val qtyRequest:TextView = findViewById(R.id.edtQuantity)
        val descriptionRequest:TextView = findViewById(R.id.edtDescription)
        val confirmBtn:Button = findViewById(R.id.edtConfirm)
        val declineButton:Button = findViewById(R.id.edtDecline)


        val bundle:Bundle ?= intent.extras
        val name = bundle?.getString("Name")
        val qty = bundle?.getInt("Qty")
        val des = bundle?.getString("Description")
        val postID = bundle?.getString("PostID")
        val reqID = bundle?.getString("ReqID")


        nameRequest.text = name
        qtyRequest.text = qty.toString()
        descriptionRequest.text = des

        confirmBtn.setOnClickListener {
            dbrefPosts.child(postID!!).child("Qty").get().addOnSuccessListener {
                if(parseInt(it.value.toString()) - qty!! <= 0) {
                    val builder = AlertDialog.Builder(this)
                    val message = "Do you want to Allocate the remaining Quantity(${it.value.toString()}L) to this user?"

                    if( parseInt(it.value.toString()) - qty < 0 ) {
                        builder.setTitle("Current Quantity exceeds requested value")
                    } else {
                        builder.setTitle("Post quantity satisfied. The post will be closed..")
                    }

                    builder.setMessage(message)
                    builder.setPositiveButton("Yes") { _, _ ->
                        Toast.makeText(this, "Request Accepted", Toast.LENGTH_LONG).show()
                        dbrefReq.child(reqID!!).removeValue()
                        dbrefPosts.child(postID).removeValue()

                        //Delete all related requests since the post is completed
                        val reqQuery : Query = dbrefReq.orderByChild("Post").equalTo(postID)
                        reqQuery.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                for (postSnapshot in dataSnapshot.children) {
//                                    Log.d(TAG,postSnapshot.value.toString())
                                    postSnapshot.ref.removeValue().addOnSuccessListener {
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
                                // ...
                            }
                        })
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                    builder.setNegativeButton("Cancel"){_, _ ->
                    }
                    val dialog = builder.create()
                    dialog.show()

                } else {
                    dbrefPosts
                        .child(postID)
                        .child("Qty")
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

        declineButton.setOnClickListener {
            dbrefReq.child(reqID!!).removeValue()
                .addOnSuccessListener {
                    val intent = Intent(this@ConfirmRequestActivity,MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Request Declined", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this@ConfirmRequestActivity, "Could not update database", Toast.LENGTH_LONG).show()
            }

        }

    }

}
