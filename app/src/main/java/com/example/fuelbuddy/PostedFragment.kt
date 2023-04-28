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
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.adapters.PostedAdapter
import com.example.fuelbuddy.dataClasses.Posted
import com.google.firebase.database.*

//import com.google.firebase.firestore.ktx.firestore

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
//        parentFragmentManager.setFragmentResult("requestKey", bundleOf("bundleKey" to totalProfit?.toString()))



//        btnDisplay.setOnClickListener {
//            Toast.makeText(context,"Hello, ${edtName.text.toString()}", Toast.LENGTH_LONG).show()
//        }
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





//        db.collection("posts")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                    val post = document.data.getValue()
//                    Log.d(TAG, post.toString())
////                    postArrayList.add(post!!)
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
    }

}