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
import java.lang.Boolean

class EditPost : AppCompatActivity() {

    //initialization
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var fuelType : EditText
    private lateinit var qty : EditText
    private lateinit var uPrice : EditText
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button
    private lateinit var userName: TextView
    var isAllFieldsChecked = false

    //declare variables and initialize to null
    var Type : String ?= null
    var Qty : Int ?= null
    var UnitProfit : Int ?= null
    var postID : String ?= null
    private var name:String ?= null

    //@SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_delete)

        //obtain an instance of posted class
        auth = FirebaseAuth.getInstance()
        //call current user to get firebase object
        val user = auth.currentUser

        //check if user is not null
        user?.let {
            name = it.displayName   //assign display name to name variable
        }

        //obtain reference to Posts node of firebase realtime database
        database = FirebaseDatabase.getInstance().getReference("Posts")
        userName = findViewById(R.id.userName)
        //display current user name
        userName.text = "$name"

        //reference to UI components
        fuelType = findViewById(R.id.fuelType)
        qty = findViewById(R.id.qtyInput)
        uPrice= findViewById(R.id.edt_unitPrice)
        btnUpdate= findViewById(R.id.btnSubmit)
        btnDelete= findViewById(R.id.btn_delete)

        //access fuel type , quantity , unitprofit passed along with intent and initialize
        val bundle: Bundle? = intent.extras
        Type = bundle?.getString("Type")
        Qty = bundle?.getInt("Qty")
        UnitProfit = bundle?.getInt("UnitPrice")
        postID = bundle?.getString("PostID")

        //set the text of EditText
        fuelType.setText(Type.toString())
        qty.setText(Qty.toString())
        uPrice.setText((UnitProfit.toString()))


        //update posts
        btnUpdate.setOnClickListener {
            //calling editPost function
            editPost()
        }

        //delete posts
        btnDelete.setOnClickListener {
            //calling deletePost function
            deletePost()
        }


    }

    private fun deletePost() {
        val post = postID.toString()       //assign post ID to post variable

        //remove post from firebase realtime database
        database.child(post).removeValue()


                //call addOnSuccessListener if post deleted successfully
            .addOnSuccessListener {
                Toast.makeText(this, "Post Deleted Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this , MainActivity::class.java)
                finish()
                startActivity(intent)
        }.addOnFailureListener {    //call addOnFailureListener if post deletion failed
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }

    private fun editPost() {
        val post = postID.toString()        //assign post ID to post variable
        val uID: String = auth.currentUser?.uid.toString()  //get current user's userID
        val editType : String ?= null
        val editQty : Int ?= null
        val editProfit : Int ?= null
        val ftype1 = "Petrol".trim()
        val ftype2 = "Diesel".trim()

//        isAllFieldsChecked = CheckAllFields().booleanValue()

        if(fuelType.text.toString().isEmpty()){
            Toast.makeText(this, "Please input fuel Type", Toast.LENGTH_SHORT).show()
//        }else if(!(fuelType.text.toString().compareTo(ftype1).equals(Boolean.TRUE) || fuelType.text.toString().compareTo(ftype2).equals(Boolean.TRUE))){
//            Toast.makeText(this, "Fuel Type is invalid", Toast.LENGTH_SHORT).show()
        }else{
            val editType = fuelType.text.toString()
        }

        if(qty.text.toString().isEmpty()){
            Toast.makeText(this, "Please input quantity", Toast.LENGTH_SHORT).show()
        }else {
            val editQty = qty.text.toString().toInt()
        }

        if(uPrice.text.toString().isEmpty()){
            Toast.makeText(this, "Please input unit profit", Toast.LENGTH_SHORT).show()
        }else {
            val editProfit = uPrice.text.toString().toInt()
        }

        if(isAllFieldsChecked) {
            //create new post of type Posted and initialize it with userID , fuelTYpe , quantity and profit
            val postDet = Posted(uID, editType , editQty , editProfit)
            Log.d(TAG, postDet.toString())

            //write data to the selected post in firebase realtime database
            database.child(post).setValue(postDet)
                .addOnCompleteListener{//call addOnSuccessListener if post updated successfully
                    //display short time notification in this activity
                    //LENGTH_SHORT , LENGTH_LONG ---> display time duration of toast
                    Toast.makeText(this, "Post Updated Successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {//call addOnFailureListener if post deletion failed
                    Toast.makeText(this,"Error" , Toast.LENGTH_LONG).show()
            }


    }

}

//private fun CheckAllFields():Boolean {
//    var ftype1 : String = "Petrol"
//    var ftype2 : String = "Diesel"
//
//    if(fuelType.text.isEmpty()){
//        Toast.makeText(this, "Please input fuel Type", Toast.LENGTH_SHORT).show()
//    }else if(!((fuelType.text)compareTo(ftype1).equals(Boolean.TRUE) || fuelType.text.compareTo(ftype2).equals(Boolean.TRUE))){
//        Toast.makeText(this, "Fuel Type is invalid", Toast.LENGTH_SHORT).show()
//    }else{
//        ftype=editType.toString()
//    }
//
//    if(editQty.isEmpty()){
//        Toast.makeText(this, "Please input quantity", Toast.LENGTH_SHORT).show()
//    }else {
//        fqty=editQty.toInt()
//    }
//
//    if(editProfit.isEmpty()){
//        Toast.makeText(this, "Please input unit profit", Toast.LENGTH_SHORT).show()
//    }else {
//        fprofit=editProfit.toInt()
//    }
}
