package com.example.fuelbuddy.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.*
import com.example.fuelbuddy.R
import com.example.fuelbuddy.adapters.vehicleAdapter
import com.example.fuelbuddy.dataClasses.Request
import com.example.fuelbuddy.dataClasses.vehicleList
import com.google.firebase.database.*

class vehicleFragment:Fragment() {
    private lateinit var dbref: DatabaseReference
    private lateinit var vehicleArrayList: ArrayList<vehicleList>
    private lateinit var vehicleRecycleView: RecyclerView
    private lateinit var btnAddVehicle : Button
    private var uid: String? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_vehicle_lobby, container, false)
        btnAddVehicle = view.findViewById(R.id.addNewVehicle)
        vehicleRecycleView = view.findViewById(R.id.vehicleList)
        vehicleRecycleView.layoutManager = LinearLayoutManager(view.context)
        vehicleRecycleView.setHasFixedSize(true)

        btnAddVehicle.setOnClickListener {
            val intent = Intent(activity,activity_add_to_car ::class.java)
            startActivity(intent)
        }

        vehicleArrayList = arrayListOf()
        vehicleRecycleView.adapter = vehicleAdapter(vehicleArrayList)

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
                    vehicleArrayList = arrayListOf()
                    var keyList = arrayListOf<String>()
                    for(getVehicle in snapshot.children) {
                        val vehicleList = getVehicle.getValue(/* valueType = */ vehicleList::class.java)
                        keyList.add(getVehicle.key.toString())
//                        request?.key = requestSnapshot.key
//                        Log.d(TAG, request.toString())
                        vehicleArrayList.add(vehicleList!!)
                    }

                    val adapter = vehicleAdapter(vehicleArrayList)

                    vehicleRecycleView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"$error", Toast.LENGTH_LONG).show()
            }

        })
    }
}


