package com.example.playlistmaker1.domain.api


import com.example.playlistmaker1.data.dto.AppleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AppleApi {

    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<AppleResponse>

}