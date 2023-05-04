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
    private lateinit var dbref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var postArrayList: ArrayList<Posted>
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
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
        emptyView = view.findViewById(R.id.postEmptyView)
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

        posts.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    postArrayList = arrayListOf()
                    val KeyList = arrayListOf<String>()
                    for (postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(/* valueType = */ Posted::class.java)
                        KeyList.add(postSnapshot.key.toString())
                        postArrayList.add(post!!)
                    }

                    val adapter = PostedAdapter(postArrayList)
                    adapter.setOnClickListener(object: PostedAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
//                            Log.d(TAG, position.toString()
                            val intent = Intent(activity , EditPost:: class.java)
                            intent.putExtra("Type",postArrayList[position].Type)
                            intent.putExtra("UserID",postArrayList[position].userID )
                            intent.putExtra("PostID", KeyList[position])
                            intent.putExtra("Qty",postArrayList[position].Qty)
                            intent.putExtra("UnitPrice",postArrayList[position].UnitProfit)
                            startActivity(intent)
                        }
                    })
                    recyclerView.adapter = adapter
                    if(postArrayList.isEmpty()) {
                        recyclerView.visibility = View.GONE
                        emptyView.visibility = View.VISIBLE
                    } else {
                        recyclerView.visibility = View.VISIBLE
                        emptyView.visibility = View.GONE
                    }
                }



            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }
        })
    }
}