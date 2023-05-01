package com.example.fuelbuddy.adapters

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.dataClasses.Posted
import com.example.fuelbuddy.R

class AllPostAdapter(private val newPostList: java.util.ArrayList<Posted>) : RecyclerView.Adapter<AllPostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val post = LayoutInflater.from(parent.context)
            .inflate(R.layout.all_post_items, parent, false)
        Log.d(TAG,newPostList.toString())
        return ViewHolder(post)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPost = newPostList[position]
        holder.fuelType.text = currentPost.Type
        holder.qty.text = currentPost.Qty.toString().plus("L")
        holder.unitProfit.text = "Rs." .plus(currentPost.UnitProfit.toString()).plus(".00")

//        holder.fuelType.text = "Petrol"
//        holder.qty.text = 10.toString()
//        holder.unitProfit.text = 100.toString()
    }

    override fun getItemCount() : Int {
        return newPostList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fuelType:TextView =  view.findViewById(R.id.Type)
        var qty: TextView = view.findViewById(R.id.quant)
        var unitProfit:TextView = view.findViewById(R.id.profit)

//        var fuelType:TextView =  view.findViewById(R.id.edtType)
//        var qty: TextView = view.findViewById(R.id.edtQTY)
//        var unitProfit:TextView = view.findViewById(R.id.edtUnitProfit)
    }
}