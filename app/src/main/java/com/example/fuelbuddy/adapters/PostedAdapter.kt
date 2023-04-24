package com.example.fuelbuddy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelbuddy.dataClasses.Posted
import com.example.fuelbuddy.R

class PostedAdapter(private val postList: java.util.ArrayList<Posted>) : RecyclerView.Adapter<PostedAdapter.ViewHolder>() {
//    private lateinit var data: List<Todo>
//    lateinit var context: Context
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
//        view.apply {
//            this.layoutParams = RecyclerView.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//        }
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.cbTodo.text = data[position].item
        val currentPost = postList[position]

//
//        holder.cbTodo.setOnClickListener {
//            if(holder.cbTodo.isChecked)
//                holder.ivDelete.setImageResource(R.drawable.clicked_delete_image)
//            else
//                holder.ivDelete.setImageResource(R.drawable.baseline_delete_forever_24)
//        }
//        holder.ivDelete.setOnClickListener {
//            if(holder.cbTodo.isChecked){
//                val repository = TodoRepository(TodoDatabase.getInstance(context))
//                holder.ivDelete.setImageResource(R.drawable.clicked_delete_image)
//                CoroutineScope(Dispatchers.IO).launch {
//                    repository.delete(data[position])
//                    val data = repository.getAllTodos()
//                    withContext(Dispatchers.Main) {
//                        setData(data, context)
//                        holder.ivDelete.setImageResource(R.drawable.baseline_delete_forever_24)
//                    }
//                }
//            }else{
//                Toast.makeText(context,"Cannot delete unmarked Todo items",Toast.LENGTH_LONG)
//                    .show()
//            }
//        }
        holder.fuelType.text = currentPost.Type
        holder.qty.text = currentPost.Qty.toString()
        holder.unitProfit.text = currentPost.UnitProfit.toString()
    }

    override fun getItemCount() = postList.size
//    override fun getItemCount() = data.size
}