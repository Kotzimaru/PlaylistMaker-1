package com.example.playlistmaker1.search.data.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/search?entity=song")
    suspend fun search(@Query("term") text: String): Response<SearchResponse>?
}

