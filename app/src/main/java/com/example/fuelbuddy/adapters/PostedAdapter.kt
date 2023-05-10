package com.example.fuelbuddy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.dataClasses.Posted
import com.example.fuelbuddy.R

class PostedAdapter(private val postList: java.util.ArrayList<Posted>) : RecyclerView.Adapter<PostedAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    //Interface to set the position
    interface onItemClickListener{
        fun onItemClick(Position:Int)
    }

    //Function to set the onItemClickListener
    fun setOnClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //ViewHolder class to set the TextViews
    class ViewHolder(view: View , listener:onItemClickListener) : RecyclerView.ViewHolder(view) {
        var qty: TextView
        var fuelType:TextView
        var unitProfit:TextView
        init {
            qty = view.findViewById(R.id.edtQTY)
            fuelType = view.findViewById(R.id.edtType)
            unitProfit = view.findViewById(R.id.edtUnitProfit)

            view.setOnClickListener{
                listener.onItemClick(bindingAdapterPosition)
            }
        }
    }

    //Function to inflate the layout with the given layout of a single item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.posted_item, parent, false)

        return ViewHolder(view , mListener)
    }
    //Function to display the data passed through requestList from requestFragment
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPost = postList[position]
        holder.fuelType.text = currentPost.Type
        holder.qty.text = currentPost.Qty.toString().plus("L")
        //Format the price before displaying
        val formattedPrice = String.format("%.2f",currentPost.UnitProfit?.toDouble())
        holder.unitProfit.text = formattedPrice
    }

    //Function to set the item count according to the data size
    override fun getItemCount() = postList.size
}