package com.example.SimpleToDo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickListener: OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // onCreateViewHolder inflates a layout for each item to get it looking how you want

        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout

        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance

        return ViewHolder(contactView)
    }
    // involves populating item data into the holder
    // will take value in list into the view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // get data model(list of items) based on the poistion

        val item = listOfItems.get(position)

        holder.textView.text = item
    }

    override fun getItemCount(): Int {

        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Store references to elements in layout view
        val textView: TextView

        init{
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener{
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}