package com.example.fuelbuddy

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.adapters.AllPostAdapter
import com.example.fuelbuddy.dataClasses.Posted
import com.google.firebase.database.*

//import com.google.firebase.firestore.ktx.firestore

class AllPostsFragment:Fragment() {
    private lateinit var dbref: DatabaseReference
    private lateinit var newPostList: ArrayList<Posted>
    private lateinit var postRecyclerView: RecyclerView
    private lateinit var btnAddFuel : ImageView

//    private var totalProfit: Int? = null
//    lateinit var type : Array<String>
//    lateinit var qty : Array<Int>
//    lateinit var profit : Array<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.post, container, false)
        btnAddFuel = view.findViewById(R.id.imgAddFuel)
        postRecyclerView = view.findViewById(R.id.allPosts)
        postRecyclerView.layoutManager = LinearLayoutManager(view.context)
        postRecyclerView.setHasFixedSize(true)

        btnAddFuel.setOnClickListener {
            val intent = Intent(activity, AddFuel::class.java)
            startActivity(intent)
        }

        newPostList = arrayListOf()
        postRecyclerView.adapter = AllPostAdapter(newPostList)
        getPostData()

        return view
    }

    private fun getPostData() {
        dbref = FirebaseDatabase.getInstance().getReference("Posts")


        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    newPostList = arrayListOf()
                    for (postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(/* valueType = */ Posted::class.java)
                        newPostList.add(post!!)
                    }
//                    Log.d(TAG , newPostList.toString())

                    postRecyclerView.adapter = AllPostAdapter(newPostList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }

        })
    }
}

