package com.example.fuelbuddy.fragments

import android.content.Intent
import android.os.Bundle
import android.service.autofill.Validators.not
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.AddFuel
import com.example.fuelbuddy.CreateRequest
import com.example.fuelbuddy.R
import com.example.fuelbuddy.adapters.AllPostAdapter
import com.example.fuelbuddy.dataClasses.Posted
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

//import com.google.firebase.firestore.ktx.firestore

class AllPostsFragment:Fragment() {
    //initialization
    private lateinit var dbref: DatabaseReference
    private lateinit var newPostList: ArrayList<Posted> //ArrayList that holds objects of Posted class
    private lateinit var postRecyclerView: RecyclerView
    private lateinit var btnAddFuel : ImageView
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //create a view from post xml
        val view = inflater.inflate(R.layout.post, container, false)
        //use the inflated view to find and interact with child views
        btnAddFuel = view.findViewById(R.id.btn_addFuel)
        postRecyclerView = view.findViewById(R.id.allPosts)
        postRecyclerView.layoutManager = LinearLayoutManager(view.context)
        postRecyclerView.setHasFixedSize(true)

        //perform action when button is clicked
        btnAddFuel.setOnClickListener {
            val intent = Intent(activity, AddFuel::class.java)
            startActivity(intent)
        }

        //create an empty arraylist
        newPostList = arrayListOf()
        //set AllPost adapter for postRecyclerView and takes an arraylist of 'Posted' objects as parameters
        postRecyclerView.adapter = AllPostAdapter(newPostList)
        getPostData()

        return view
    }

    private fun getPostData() {
        //call current user to get firebase object
        dbref = FirebaseDatabase.getInstance().getReference("Posts")
        //obtain an instance of posted class
        auth = FirebaseAuth.getInstance()
        //call current user to get firebase object
        val user = auth.currentUser

        dbref.addValueEventListener(object : ValueEventListener { //valueEventListener listen changes in post at realtimme databse
            override fun onDataChange(snapshot: DataSnapshot) { //snapshot is the current state of data
                if (snapshot.exists()) {
                    newPostList = arrayListOf()
                    //arraylist with key values
                    val keyList = arrayListOf<String>()
                    for (postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(/* valueType = */ Posted::class.java)
                        newPostList.add(post!!)
                        keyList.add(postSnapshot.key.toString())
                    }
//                    Log.d(TAG , newPostList.toString())

                    //create an instance of the AllPostAdapter
                    // setting an item click listener on the adapter and defining the behavior for when a post is clicked
                    val adapter = AllPostAdapter(newPostList)
                    adapter.setOnItemClickListener(object: AllPostAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
//                            Log.d(TAG, position.toString())
                            val intent = Intent(activity, CreateRequest::class.java)
                            intent.putExtra("postUserID",newPostList[position].userID)
                            intent.putExtra("Price",newPostList[position].UnitProfit)
                            intent.putExtra("qty",newPostList[position].Qty)
                            intent.putExtra("Post",keyList[position])
                            startActivity(intent)
                        }
                    })
                    postRecyclerView.adapter = adapter
                }
            }

            //call when database operation is canceled
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }

        })
    }
}

