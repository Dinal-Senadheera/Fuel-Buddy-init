package com.example.fuelbuddy.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.EditPost
import com.example.fuelbuddy.Login
import com.example.fuelbuddy.R
import com.example.fuelbuddy.adapters.PostedAdapter
import com.example.fuelbuddy.dataClasses.Posted
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class PostedFragment:Fragment() {
    // database reference to fetch requests
    private lateinit var dbref: DatabaseReference
    // firebase auth reference to authenticate user
    private lateinit var auth: FirebaseAuth
    // postArrayList to store the fetched data
    private lateinit var postArrayList: ArrayList<Posted>
    // recyclerView variable for the RecyclerView widge
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    // uid to get the uid of the logged user
    private var uid:String ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // firebase authentication to get the current user
        auth = FirebaseAuth.getInstance()
        // get current user
        val user = auth.currentUser
        // check if the user is logged in
        if( user == null) {
            startActivity(Intent(activity, Login::class.java))
        }
        uid = user?.uid

        // inflate view with the layout.xml file for the fragment
        val view = inflater.inflate(R.layout.posted_fragment, container, false)
        // set relevant widgets
        emptyView = view.findViewById(R.id.postEmptyView)
        recyclerView = view.findViewById(R.id.postedList)

        // set recycler view properties
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.setHasFixedSize(true)
        // initialize array list
        postArrayList = arrayListOf()
        recyclerView.adapter = PostedAdapter(postArrayList)
        // call getPostData function
        getPostData()

        return view
    }

    // getRequestData function to fetch the requests data from the realtime database
    private fun getPostData() {
        // initialize database with relevant path
        dbref = FirebaseDatabase.getInstance().getReference("Posts")

        // query the requests that the current user should see
        val posts : Query = dbref.orderByChild("userID").equalTo(uid)

        posts.addValueEventListener(object: ValueEventListener {
            //override onDataChange function so that the data in the recycle View is updated on data change
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // make sure the requestArrayList is empty when starting the loop to make sure there
                    // is no duplicate data
                    postArrayList = arrayListOf()
                    // initialize an arrayList to store the key of the request
                    val keyList = arrayListOf<String>()
                    // for loop to store the data of each post
                    for (postSnapshot in snapshot.children) {
                        // post variable to get data of one post
                        val post = postSnapshot.getValue(/* valueType = */ Posted::class.java)
                        // get the key of the request and store in keyList arrayList
                        keyList.add(postSnapshot.key.toString())
                        // store the fetched data in arrayList
                        postArrayList.add(post!!)
                    }

                    // set the adapter with the postArrayList with fetched data
                    val adapter = PostedAdapter(postArrayList)

                    // call setOnItemClickListener to set the onItemClick listener
                    adapter.setOnClickListener(object: PostedAdapter.onItemClickListener{
                        override fun onItemClick(Position: Int) {
                            // Direct the user to EditPost Activity when a request is clicked
                            val intent = Intent(activity , EditPost:: class.java)
                            // pass necessary data through intent
                            intent.putExtra("Type",postArrayList[Position].Type)
                            intent.putExtra("UserID",postArrayList[Position].userID )
                            intent.putExtra("PostID", keyList[Position])
                            intent.putExtra("Qty",postArrayList[Position].Qty)
                            intent.putExtra("UnitPrice",postArrayList[Position].UnitProfit)
                            // start the new activity
                            startActivity(intent)
                        }
                    })

                    // set the adapter
                    recyclerView.adapter = adapter
                    // show custom message if there are no posts
                    if(postArrayList.isEmpty()) {
                        recyclerView.visibility = View.GONE
                        emptyView.visibility = View.VISIBLE
                    } else {
                        recyclerView.visibility = View.VISIBLE
                        emptyView.visibility = View.GONE
                    }
                }
            }

            // show errors if any errors occur in the database retrieval
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }
        })
    }
}