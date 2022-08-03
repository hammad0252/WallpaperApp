package com.company.wallpaperapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class WallpaperAdapter (private val wallpaperList : MutableList<String>, private val context: Context): RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wallpaper_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load(wallpaperList[position])
            .resize(250, 420)
            .centerCrop()
            .into(holder.wallpaperView)

        holder.itemView.setOnClickListener{
            val intent = Intent(context,WallpaperActivity::class.java)
            intent.putExtra("url", wallpaperList[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return wallpaperList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val wallpaperView: ImageView = itemView.findViewById(R.id.wallpaperView)
    }
}