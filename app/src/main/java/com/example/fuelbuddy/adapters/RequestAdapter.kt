package com.example.fuelbuddy.adapters

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.R
import com.example.fuelbuddy.dataClasses.Request

class RequestAdapter(private val requestList: java.util.ArrayList<Request>) : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var qty: TextView
        var name:TextView
//        var unitProfit:TextView
        init {
            qty = view.findViewById(R.id.edtReqQty)
            name = view.findViewById(R.id.edtName)
//            unitProfit = view.findViewById(R.id.edtUnitProfit)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.requested_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.cbTodo.text = data[position].item
        val currentRequest = requestList[position]

//        holder.name.text = currentRequest.Name
//        holder.qty.text = currentRequest.Qty.toString()
//        holder.unitProfit.text = currentRequest.UnitProfit.toString()

        holder.name.text = currentRequest.Name
        holder.qty.text = currentRequest.Qty.toString()
    }

//    override fun getItemCount() = requestList.size
    override fun getItemCount() = requestList.size

}