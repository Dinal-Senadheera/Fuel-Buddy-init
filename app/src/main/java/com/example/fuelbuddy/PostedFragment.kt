package com.example.fuelbuddy

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.adapters.PostedAdapter
import com.example.fuelbuddy.dataClasses.Posted
import com.google.firebase.database.*


class PostedFragment:Fragment() {
    private lateinit var dbref: DatabaseReference
    private lateinit var postArrayList: ArrayList<Posted>
    private lateinit var recyclerView: RecyclerView
//    private var totalProfit: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
//                    totalProfit = 0
                    postArrayList = arrayListOf()
                    for(postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(/* valueType = */ Posted::class.java)
//                        this@PostedFragment.totalProfit = totalProfit!! + (post?.Qty?.times(post.UnitProfit!!)!!)
                        postArrayList.add(post!!)
                    }
//                    Log.d(TAG, totalProfit.toString())

                    recyclerView.adapter = PostedAdapter(postArrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"$error", Toast.LENGTH_LONG).show()
            }

        })

    }

}