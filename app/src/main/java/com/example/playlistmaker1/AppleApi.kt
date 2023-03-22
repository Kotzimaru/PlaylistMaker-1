package com.example.playlistmaker1


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AppleApi {

    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<AppleResponse>
}

class SearchResponse(val results: ArrayList<Track>)