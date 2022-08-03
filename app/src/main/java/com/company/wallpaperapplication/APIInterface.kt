package com.company.wallpaperapplication

import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @GET("/api/")
    suspend fun getWallpapers(@Query("key") key : String, @Query("q") search : String, @Query("page") page : Int, @Query("per_page") perPage : Int, @Query("orientation") orientation : String) : Response<PixabyHigh>
}
