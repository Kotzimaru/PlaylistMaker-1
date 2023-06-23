package com.example.playlistmaker1.search.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String?): Call<AppleResponse>
}
