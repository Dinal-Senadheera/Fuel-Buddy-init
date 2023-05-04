package com.example.fuelbuddy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.fuelbuddy.databinding.ActivityConfirmPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditPost : AppCompatActivity() {

//    private lateinit var binding: Activity
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
//    private lateinit var fuelType : EditText
//    private lateinit var qty : EditText
//    private lateinit var uPrice : EditText
    private lateinit var btnUpdate : Button
//    private lateinit var btnDelete : Button
    private lateinit var userName: TextView
//    var Type : String ?= null
//    var Qty : Int ?= null
//    var UnitProfit : Int ?= null
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
//
//        val type: EditText = findViewById(R.id.fuelType)
//        val fqty: EditText = findViewById(R.id.qtyInput)
//        val price: EditText = findViewById(R.id.edt_unitPrice)
//        val update: Button = findViewById(R.id.btnUpdate)
//        val delete: Button = findViewById(R.id.btn_delete)
//

//        val bundle: Bundle? = intent.extras
//        Type = bundle?.getString("Type")
//        Qty = bundle?.getInt("Qty")
//        UnitProfit = bundle?.getInt("UnitProfit")
//
//        fuelType.setText(Type.toString())
//        qty.setText(Qty.toString())
//        uPrice.setText((UnitProfit.toString()))
//
 //       btnUpdate.setOnClickListener{
//            updatePosts()
//
 //           var intent = Intent(this,MainActivity::class.java)
  //          startActivity(intent)
  //      }


    }

//    private fun updatePosts(String ) {
//
//    }
}