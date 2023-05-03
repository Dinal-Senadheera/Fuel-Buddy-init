package com.example.fuelbuddy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.R
import com.example.fuelbuddy.dataClasses.Request
import com.example.fuelbuddy.dataClasses.vehicleList

class RequestAdapter(private val requestList: ArrayList<Request>) : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }


    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener = listener
    }
    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        var qty: TextView
        var name:TextView
        //        var unitProfit:TextView
        init {
            qty = view.findViewById(R.id.edtReqQty)
            name = view.findViewById(R.id.edtName)
//            unitProfit = view.findViewById(R.id.edtUnitProfit)

            view.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.requested_item, parent, false)
        return ViewHolder(view ,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.cbTodo.text = data[position].item
        val currentRequest = requestList[position]

        holder.name.text = currentRequest.Name
        holder.qty.text = currentRequest.Qty.toString()
    }



    override fun getItemCount() = requestList.size

}