package com.example.fuelbuddy.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.Login
import com.example.fuelbuddy.R
import com.example.fuelbuddy.adapters.PostedAdapter
import com.example.fuelbuddy.dataClasses.Posted
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class PostedFragment:Fragment() {
    private lateinit var dbref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var postArrayList: ArrayList<Posted>
    private lateinit var recyclerView: RecyclerView
    private var uid:String ?= null
//    private var totalProfit: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if( user == null) {
            startActivity(Intent(activity, Login::class.java))
        }
        uid = user?.uid

        val view = inflater.inflate(R.layout.posted_fragment, container, false)
        recyclerView = view.findViewById(R.id.postedList)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.setHasFixedSize(true)

        postArrayList = arrayListOf()
        recyclerView.adapter = PostedAdapter(postArrayList)
        getPostData()

        return view
    }


    private fun getPostData() {
        dbref = FirebaseDatabase.getInstance().getReference("Posts")

        val posts : Query = dbref.orderByChild("userID").equalTo(uid)

        posts.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    postArrayList = arrayListOf()
                    for(postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(/* valueType = */ Posted::class.java)
                        postArrayList.add(post!!)
                    }

                    recyclerView.adapter = PostedAdapter(postArrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"$error", Toast.LENGTH_LONG).show()
            }

        })

    }

}