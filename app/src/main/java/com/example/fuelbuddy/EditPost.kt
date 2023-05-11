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
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import com.example.fuelbuddy.MainActivity
import com.example.fuelbuddy.R
import com.example.fuelbuddy.dataClasses.Posted
import com.example.fuelbuddy.fragments.PostedFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class EditPost : AppCompatActivity() {

    //initialization
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var dbReq : DatabaseReference
    private lateinit var fuelType : EditText
    private lateinit var qty : EditText
    private lateinit var uPrice : EditText
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button
    private lateinit var userName: TextView

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
        dbReq = FirebaseDatabase.getInstance().getReference("Requests")
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
                // delete all the related requests
                val reqQuery : Query = dbReq.orderByChild("post").equalTo(post)
                reqQuery.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (postSnapshot in dataSnapshot.children) {
                            postSnapshot.ref.removeValue().addOnSuccessListener {
                                Toast.makeText(this@EditPost, "Post Deleted Successfully", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@EditPost , MainActivity::class.java)
                                finish()
                                startActivity(intent)
                            }.addOnFailureListener {
                                Toast.makeText(
                                    this@EditPost,
                                    "Could not update database",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            }.addOnFailureListener {    //call addOnFailureListener if post deletion failed
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }


    }

    private fun editPost() {
        val post = postID.toString()        //assign post ID to post variable
        val uID: String = auth.currentUser?.uid.toString()  //get current user's userID
        var count = 0
        var ftype1 = "petrol"
        var ftype2 = "diesel"

        if(fuelType.text.toString().lowercase().isEmpty()) {
            Toast.makeText(this, "Please input fuelType", Toast.LENGTH_SHORT).show()
            count++
        }else if (!(fuelType.text.toString().lowercase() == ftype1 || fuelType.text.toString().lowercase()==ftype2)) {
            Toast.makeText(this, "Fuel Type is invalid", Toast.LENGTH_SHORT).show()
            count++
        }
        if (qty.text.toString().isEmpty()) {
            Toast.makeText(this, "Please input quantity", Toast.LENGTH_SHORT).show()
            count++
        }
        if (uPrice.text.toString().isEmpty()){
            Toast.makeText(this, "Please input unit profit", Toast.LENGTH_SHORT).show()
            count++
        }

        if(count == 0){
            val editType = fuelType.text.toString()
            val editQty = qty.text.toString().toInt()
            val editProfit = uPrice.text.toString().toInt()

            //calculate and format profit
            val profit = calculateProfit(editQty!! ,editProfit!! , 1)
            var formattedProfit = String.format("%.2f" , profit.totalProfit())


            //alert dialog
            val builder = AlertDialog.Builder(this)
            val message = "Do you want to update post?"
            builder.setTitle("Your total will be Rs ".plus(formattedProfit))
            builder.setMessage(message)
            builder.setPositiveButton("Yes") { _, _ ->
                //create new post of type Posted and initialize it with userID , fuelTYpe , quantity and profit
                val postDet = Posted(uID, editType, editQty, editProfit)
                Log.d(TAG, postDet.toString())


                //write data to the selected post in firebase realtime database
                database.child(post).setValue(postDet)
                    .addOnCompleteListener {//call addOnSuccessListener if post updated successfully
                        //display short time notification in this activity
                        //LENGTH_SHORT , LENGTH_LONG ---> display time duration of toast
                        Toast.makeText(this, "Post Updated Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }.addOnFailureListener {//call addOnFailureListener if post deletion failed
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    }}
            builder.setNegativeButton("Cancel"){_, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            val dialog = builder.create()
            dialog.show()
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }

    }

}
