package com.company.wallpaperapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CategoryAdapter (private val context: Context, private val categoryList : MutableList<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = categoryList[position].categoryName
        Picasso
            .get()
            .load(categoryList[position]
            .categoryUrl)
            .resize(260, 140) // resizes the image to these dimensions (in pixel)
            .centerCrop()
            .into(holder.imageView);
        holder.itemView.setOnClickListener{
            (context as MainActivity).CategoryClick(position)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}