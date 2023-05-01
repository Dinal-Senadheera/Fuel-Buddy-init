package com.example.fuelbuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fuelbuddy.dataClasses.Posted
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
class MenuFragment: Fragment() {
    private val fragmentPosted = PostedFragment()
    private val requestFragment = RequestFragment()
    private lateinit var totalProfit: TextView
    private lateinit var username: TextView
    private lateinit var requestBtn: Button
    private lateinit var postBtn: Button
    private lateinit var auth: FirebaseAuth
    private var name:String ?= null
    private var uid:String ?= null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        user?.let {
            name = it.displayName
            uid = it.uid
        }
        val view = inflater.inflate(R.layout.menu_fragment, container, false)
        requestBtn = view.findViewById(R.id.edtRequests)
        postBtn = view.findViewById(R.id.edtPosted)
        totalProfit = view.findViewById(R.id.edtProfit)
        username = view.findViewById(R.id.Username)
        username.text = "Hello $name"


        getTotalProfitData()
        childFragmentManager.beginTransaction().apply {
            replace(R.id.postedInit, fragmentPosted)
            commit()
        }

//        childFragmentManager.setFragmentResultListener("requestKey",this) { key, bundle ->
//            val result = bundle.getString("bundleKey")
//            if (result != null) {
//                Log.d(TAG, result)
//            }
//            val profit = "Rs.$result"
//            totalProfit.text = profit
//        }

        requestBtn.setOnClickListener {
            requestBtn.setBackgroundColor(resources.getColor(R.color.primary))
            postBtn.setBackgroundColor(resources.getColor(R.color.background))
            childFragmentManager.beginTransaction().apply {
                replace(R.id.postedInit, requestFragment)
                commit()
            }
        }

        postBtn.setOnClickListener {
            postBtn.setBackgroundColor(resources.getColor(R.color.primary))
            requestBtn.setBackgroundColor(resources.getColor(R.color.background))
            childFragmentManager.beginTransaction().apply {
                replace(R.id.postedInit, fragmentPosted)
                commit()
            }
        }
        return view
    }

    private fun getTotalProfitData() {
        val dbref = FirebaseDatabase.getInstance().getReference("Posts")
        dbref.keepSynced(true)

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var total = 0
                    for (postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue( Posted::class.java)
                        total += (post?.Qty?.times(post.UnitProfit!!)!!)
                    }
//                    Log.d(TAG, totalProfit.toString())
                    totalProfit.text = "Rs.$total"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }

        })

    }
}