package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.networking.Post

class ItemAdapter(
    private val context: Context,
    private var posts: MutableList<Post>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var titleView: TextView = view.findViewById(R.id.title)
        var bodyView: TextView = view.findViewById(R.id.body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = posts[position]
        holder.bodyView.setText(item.description)
        holder.titleView.setText(item.title)
    }
}