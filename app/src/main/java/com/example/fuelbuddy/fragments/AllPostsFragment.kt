package com.example.fuelbuddy.fragments

import android.content.Intent
import android.os.Bundle
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
import com.google.firebase.database.*

//import com.google.firebase.firestore.ktx.firestore

class AllPostsFragment:Fragment() {
    private lateinit var dbref: DatabaseReference
    private lateinit var newPostList: ArrayList<Posted>
    private lateinit var postRecyclerView: RecyclerView
    private lateinit var btnAddFuel : ImageView

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

//        val posts : Query = dbref.orderByChild("userID").whereNotEqualTo("UserID",)
//        pass uerID and check the userID before this query

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    newPostList = arrayListOf()
                    val keyList = arrayListOf<String>()
                    for (postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(/* valueType = */ Posted::class.java)
                        newPostList.add(post!!)
                        keyList.add(postSnapshot.key.toString())
                    }
//                    Log.d(TAG , newPostList.toString())

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

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }

        })
    }
}

