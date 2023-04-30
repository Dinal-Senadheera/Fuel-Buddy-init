package com.example.fuelbuddy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.dataClasses.Posted
import com.example.fuelbuddy.R

class PostedAdapter(private val postList: java.util.ArrayList<Posted>) : RecyclerView.Adapter<PostedAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var qty: TextView
        var fuelType:TextView
        var unitProfit:TextView
        init {
            qty = view.findViewById(R.id.edtQTY)
            fuelType = view.findViewById(R.id.edtType)
            unitProfit = view.findViewById(R.id.edtUnitProfit)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.posted_item, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.cbTodo.text = data[position].item
        val currentPost = postList[position]
        holder.fuelType.text = currentPost.Type
        holder.qty.text = currentPost.Qty.toString()
        holder.unitProfit.text = currentPost.UnitProfit.toString()
    }

    override fun getItemCount() = postList.size
//    override fun getItemCount() = data.size
}