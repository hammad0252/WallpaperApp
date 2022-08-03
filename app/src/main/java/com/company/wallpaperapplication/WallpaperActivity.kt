package com.company.wallpaperapplication

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.wallpaperapplication.databinding.ActivityWallpaperBinding
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread


class WallpaperActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWallpaperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wallpaper : String = intent.extras?.getString("url").toString()

        Picasso.get()
            .load(wallpaper)
            .resize(1080, 1920)
            .centerCrop()
            .into(binding.fullWallpaperView)

        binding.wallpaperButton.setOnClickListener{
            thread {
                val myBitmap : Bitmap = Picasso.get().load(wallpaper).get()
                val wallpaperManager = WallpaperManager.getInstance(applicationContext)
                wallpaperManager.setBitmap(myBitmap) }
            }
    }
}
