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
import com.example.fuelbuddy.ConfirmRequestActivity
import com.example.fuelbuddy.Login
import com.example.fuelbuddy.R
import com.example.fuelbuddy.adapters.RequestAdapter
import com.example.fuelbuddy.dataClasses.Request
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RequestFragment:Fragment() {
    // database reference to fetch requests
    private lateinit var dbref: DatabaseReference
    // firebase auth reference to authenticate user
    private lateinit var auth: FirebaseAuth
    // requestArrayList to store the fetched data
    private lateinit var requestArrayList: ArrayList<Request>
    // recyclerView variable for the RecyclerView widget
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
        val view = inflater.inflate(R.layout.requests_fragment, container, false)
        // set relevant widgets
        emptyView = view.findViewById(R.id.requestEmptyView)
        recyclerView = view.findViewById(R.id.requestList)

        // set recycler view properties
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.setHasFixedSize(true)
        // initialize array list
        requestArrayList = arrayListOf()
        recyclerView.adapter = RequestAdapter(requestArrayList)
        // call getRequestData function
        getRequestData()

        return view
    }


    // getRequestData function to fetch the requests data from the realtime database
    private fun getRequestData() {
        // initialize database with relevant path
        dbref = FirebaseDatabase.getInstance().getReference("Requests")

        // query the requests that the current user should see
        val requests : Query = dbref.orderByChild("postUserID").equalTo(uid)

        requests.addValueEventListener(object: ValueEventListener {
            //override onDataChange function so that the data in the recycle View is updated on data change
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    // make sure the requestArrayList is empty when starting the loop to make sure there
                    // is no duplicate data
                    requestArrayList = arrayListOf()
                    // initialize an arrayList to store the key of the request
                    val keyList = arrayListOf<String>()
                    // for loop to store the data of each request
                    for(requestSnapshot in snapshot.children) {
                        // request variable to get data of one request
                        val request = requestSnapshot.getValue(/* valueType = */ Request::class.java)
                        // get the key of the request and store in keyList arrayList
                        keyList.add(requestSnapshot.key.toString())
                        // store the fetched data in arrayList
                        requestArrayList.add(request!!)
                    }

                    // set the adapter with the requestArrayList with fetched data
                    val adapter = RequestAdapter(requestArrayList)

                    // call setOnItemClickListener to set the onItemClick listener
                    adapter.setOnItemClickListener(object:RequestAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            // Direct the user to Confirm Request Activity when a request is clicked
                            val intent = Intent(activity, ConfirmRequestActivity::class.java)
                            // pass necessary data through intent
                            intent.putExtra("Name",requestArrayList[position].Name)
                            intent.putExtra("PostID",requestArrayList[position].Post)
                            intent.putExtra("ReqID",keyList[position])
                            intent.putExtra("Qty",requestArrayList[position].Qty)
                            intent.putExtra("Description",requestArrayList[position].Description)
                            // start the new activity
                            startActivity(intent)
                        }
                    })

                    // set the adapter
                    recyclerView.adapter = adapter
                    // show custom message if there are no requests
                    if(requestArrayList.isEmpty()) {
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
                Toast.makeText(context,"$error", Toast.LENGTH_LONG).show()
            }

        })
    }

}