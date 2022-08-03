package com.company.wallpaperapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.wallpaperapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager : GridLayoutManager
    private val categoryList = mutableListOf<Category>()
    private val wallpaperList = mutableListOf<String>()
    private val wallpaperAdapter = WallpaperAdapter(wallpaperList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getCategories()
        val categoryAdapter = CategoryAdapter(this,categoryList)
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.horizontalRecyclerView.layoutManager = linearLayoutManager
        binding.horizontalRecyclerView.adapter = categoryAdapter
        binding.horizontalRecyclerView.addItemDecoration(DividerItemDecoration(baseContext,linearLayoutManager.orientation))

        lifecycleScope.launchWhenCreated { getWallpapers() }
        gridLayoutManager = GridLayoutManager(this,3)
        binding.gridRecyclerView.layoutManager = gridLayoutManager
        binding.gridRecyclerView.adapter = wallpaperAdapter
        binding.gridRecyclerView.addItemDecoration(DividerItemDecoration(baseContext,gridLayoutManager.orientation))

        binding.searchIcon.setOnClickListener{
            if (binding.search.text.toString() == ""){
                Snackbar.make(
                    view,
                    "Enter Text to Search",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                lifecycleScope.launchWhenCreated { getWallpapers(binding.search.text.toString())}
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun getWallpapers(searchTerm : String = "landscape") {
        binding.progressBar.visibility = android.view.View.VISIBLE
        wallpaperList.clear()
        val response = RetrofitAPI.api.getWallpapers("28971617-2b8ecdb6dca18eaed3d962c86",searchTerm, 1, 30, "vertical")
        Log.d("TAG", "getWallpapers: $response")
        if (response.isSuccessful) {
            response.body()?.hits?.forEach { item ->
                wallpaperList.add(item.largeImageURL)
            }
            wallpaperAdapter.notifyDataSetChanged()
            binding.progressBar.visibility = android.view.View.GONE
        }
    }

    private fun getCategories() {
        categoryList.add(Category("Landscape","https://wallpaperaccess.com/full/170249.jpg"))
        categoryList.add(Category("Abstract","https://images.pexels.com/photos/2693212/pexels-photo-2693212.png"))
        categoryList.add(Category("Movies","https://images.alphacoders.com/270/270963.jpg"))
        categoryList.add(Category("Colors","https://wallpaper.dog/large/20512775.jpg"))
    }

    internal fun CategoryClick(position: Int){
        lifecycleScope.launchWhenCreated { getWallpapers(categoryList[position].categoryName) }
    }
}