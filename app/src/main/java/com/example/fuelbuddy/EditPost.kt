package com.example.fuelbuddy

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.fuelbuddy.dataClasses.Posted
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditPost : AppCompatActivity() {


    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var fuelType : EditText
    private lateinit var qty : EditText
    private lateinit var uPrice : EditText
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button
    private lateinit var userName: TextView
    var Type : String ?= null
    var Qty : Int ?= null
    var UnitProfit : Int ?= null
    var postID : String ?= null
    private var name:String ?= null

    //@SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_delete)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        user?.let {
            name = it.displayName
        }

        database = FirebaseDatabase.getInstance().getReference("Posts")
        userName = findViewById(R.id.userName)
        userName.text = "$name"

        fuelType = findViewById(R.id.fuelType)
        qty = findViewById(R.id.qtyInput)
        uPrice= findViewById(R.id.edt_unitPrice)
        btnUpdate= findViewById(R.id.btnSubmit)
        btnDelete= findViewById(R.id.btn_delete)

        val bundle: Bundle? = intent.extras
        Type = bundle?.getString("Type")
        Qty = bundle?.getInt("Qty")
        UnitProfit = bundle?.getInt("UnitPrice")
        postID = bundle?.getString("PostID")


        fuelType.setText(Type.toString())
        qty.setText(Qty.toString())
        uPrice.setText((UnitProfit.toString()))


        btnUpdate.setOnClickListener {
            editPost()
        }

        btnDelete.setOnClickListener {
            deletePost()
        }


    }

    private fun deletePost() {
        val post = postID.toString()

        database.child(post).removeValue().addOnSuccessListener {
            Toast.makeText(this, "Post Deleted Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this , MainActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }

    private fun editPost() {
        val post = postID.toString()
        val uID: String = auth.currentUser?.uid.toString()
        val editType = fuelType.text.toString()
        val editQty = qty.text.toString().toInt()
        val editProfit = uPrice.text.toString().toInt()

        val postDet = Posted(uID , editType ,editQty , editProfit)
        Log.d(TAG,postDet.toString())

        database.child(post).setValue(postDet).addOnCompleteListener{
            Toast.makeText(this, "Post Updated Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this,"Error" , Toast.LENGTH_LONG).show()
        }


    }
}
