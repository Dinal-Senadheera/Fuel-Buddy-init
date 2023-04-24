package com.example.fuelbuddy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.adapters.RequestAdapter
import com.example.fuelbuddy.dataClasses.Request
import com.google.firebase.database.*

class RequestFragment:Fragment() {
    private lateinit var dbref: DatabaseReference
    private lateinit var requestArrayList: ArrayList<Request>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.requests_fragment, container, false)
        recyclerView = view.findViewById(R.id.requestList)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.setHasFixedSize(true)

        requestArrayList = arrayListOf<Request>()
        recyclerView.adapter = RequestAdapter(requestArrayList)
        getRequestData()

        return view
    }

    private fun getRequestData() {
//        db = Firebase.firestore
        dbref = FirebaseDatabase.getInstance().getReference("Requests")

//        Log.d(TAG,"ComesHere")

        dbref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    requestArrayList = arrayListOf<Request>()
                    for(requestSnapshot in snapshot.children) {
                        val request = requestSnapshot.getValue(/* valueType = */ Request::class.java)
//                        Log.d(TAG, request.toString())
                        requestArrayList.add(request!!)
                    }

                    recyclerView.adapter = RequestAdapter(requestArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}