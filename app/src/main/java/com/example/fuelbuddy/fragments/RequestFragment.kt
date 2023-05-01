package com.example.fuelbuddy.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.ConfirmRequestActivity
import com.example.fuelbuddy.Login
import com.example.fuelbuddy.R
import com.example.fuelbuddy.adapters.RequestAdapter
import com.example.fuelbuddy.dataClasses.Request
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RequestFragment:Fragment() {
    private lateinit var dbref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var requestArrayList: ArrayList<Request>
    private lateinit var recyclerView: RecyclerView
    private var uid:String ?= null


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

        val view = inflater.inflate(R.layout.requests_fragment, container, false)

        recyclerView = view.findViewById(R.id.requestList)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.setHasFixedSize(true)
        requestArrayList = arrayListOf()
        recyclerView.adapter = RequestAdapter(requestArrayList)
        getRequestData()



        return view
    }


    private fun getRequestData() {
//        db = Firebase.firestore
        dbref = FirebaseDatabase.getInstance().getReference("Requests")

        val requests : Query = dbref.orderByChild("postUserID").equalTo(uid)

        requests.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    requestArrayList = arrayListOf()
                    for(requestSnapshot in snapshot.children) {
                        val request = requestSnapshot.getValue(/* valueType = */ Request::class.java)
                        request?.key = requestSnapshot.key
                        Log.d(TAG, request.toString())
                        requestArrayList.add(request!!)
                    }

                    val adapter = RequestAdapter(requestArrayList)
                    adapter.setOnItemClickListener(object:RequestAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
//                            Log.d(TAG, position.toString())
                            val intent = Intent(activity, ConfirmRequestActivity::class.java)
//                            intent.putExtra("request", requestArrayList[position])
                            intent.putExtra("Name",requestArrayList[position].Name)
                            intent.putExtra("PostID",requestArrayList[position].Post)
                            intent.putExtra("ReqID",requestArrayList[position].key)
                            intent.putExtra("Qty",requestArrayList[position].Qty)
                            intent.putExtra("Description",requestArrayList[position].Description)
                            startActivity(intent)
                        }
                    })
                    recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"$error", Toast.LENGTH_LONG).show()
            }

        })
    }

}