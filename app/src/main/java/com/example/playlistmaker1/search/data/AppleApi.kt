package com.example.playlistmaker1.search.data

import com.example.playlistmaker1.player.domain.TrackDTO
import com.example.playlistmaker1.search.domain.Uploader
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://itunes.apple.com/"
class AppleApi {

    private val api: SearchApi = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create()).build().create()

    fun evaluateRequest(text: String,uploader: Uploader) {

        api.search(text).enqueue(object : Callback<AppleResponse> {

            override fun onFailure(call: Call<AppleResponse>, t: Throwable) {
                uploader.getTracks(null)
            }

            override fun onResponse(
                call: Call<AppleResponse>,
                response: Response<AppleResponse>
            ) {

                if (response.isSuccessful) {
                    val trackJSON = response.body()?.results
                    if (trackJSON != null) {
                        uploader.getTracks(trackJSON as ArrayList<TrackDTO>?)
                    }
                }
            }
        })
    }
}