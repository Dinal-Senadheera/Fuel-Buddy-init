package com.example.fuelbuddy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.R
import com.example.fuelbuddy.dataClasses.vehicleList

class vehicleAdapter(private val vehicleList : ArrayList<vehicleList>) :
    RecyclerView.Adapter<vehicleAdapter.vehicleViewHolder>() {
    private lateinit var vehiLis :onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vehicle_items, parent, false)
        return vehicleViewHolder(view)
    }

    fun setOnItemClickListener(listener:onItemClickListener) {

        vehiLis = listener
    }
    override fun getItemCount(): Int {
        return vehicleList.size
    }

    override fun onBindViewHolder(holder: vehicleViewHolder, position: Int) {

        val currentPost = vehicleList[position]

        holder.vehicleName.text = currentPost.vehiType
        holder.vehicleNumber.text = currentPost.vehinum

    }

    class vehicleViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val vehicleName : TextView = view.findViewById(R.id.vehiName)
        val vehicleNumber : TextView = view.findViewById(R.id.vehicleNumber)

    }
}