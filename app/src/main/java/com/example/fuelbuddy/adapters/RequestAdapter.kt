package com.example.fuelbuddy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.R
import com.example.fuelbuddy.dataClasses.Request

class RequestAdapter(private val requestList: ArrayList<Request>) : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    //Interface to set the position
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    //Function to set the onItemClickListener
    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    //ViewHolder class to set the TextViews
    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        var qty: TextView
        var name:TextView
        init {
            qty = view.findViewById(R.id.edtReqQty)
            name = view.findViewById(R.id.edtName)

            view.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }

        }
    }

    //Function to inflate the layout with the given layout of a single item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.requested_item, parent, false)
        return ViewHolder(view ,mListener)
    }

    //Function to display the data passed through requestList from requestFragment
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRequest = requestList[position]

        holder.name.text = currentRequest.Name
        holder.qty.text = currentRequest.Qty.toString().plus("L")
    }

    //Function to set the item count according to the data size
    override fun getItemCount() = requestList.size

}