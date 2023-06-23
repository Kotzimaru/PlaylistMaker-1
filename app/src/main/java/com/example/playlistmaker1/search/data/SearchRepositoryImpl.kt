package com.example.playlistmaker1.search.data

import android.content.SharedPreferences
import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.search.data.network.AppleResponse
import com.example.playlistmaker1.search.data.network.SearchApi
import com.example.playlistmaker1.search.data.network.SearchSerializator
import com.example.playlistmaker1.search.domain.Uploader
import com.example.playlistmaker1.search.domain.api.SearchRepository
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SearchRepositoryImpl(private val sharedPrefs: SharedPreferences): SearchRepository {


    companion object {
        val serializator = SearchSerializator()
        const val KEY_LIST_TRACKS = "tracks_history"
        const val URL = "https://itunes.apple.com/"

    }



    private val retrofit: SearchApi = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create()).build().create()



    override fun getHistory(): ArrayList<TrackDTO> {
        val stringHistory = sharedPrefs.getString(KEY_LIST_TRACKS, "")

        if (stringHistory?.isEmpty() == true) return ArrayList()

        return serializator.jsonToTracks(stringHistory)
    }


    override fun setHistory(trackList: ArrayList<TrackDTO>?) {
        sharedPrefs.edit()?.putString(
            KEY_LIST_TRACKS, serializator.tracksToJson(
                trackList,
            )
        )
            ?.apply()
    }

    override fun removeTrack(trackList: ArrayList<TrackDTO>, track: TrackDTO): ArrayList<TrackDTO> {

        trackList.remove(track)
        trackList.add(0, track)

        if (trackList.size!! > 10) {
            trackList.removeLast()
        }
        setHistory(trackList)
        return trackList
    }

    override fun searchTrack(text: String, uploader: Uploader) {

        retrofit.search(text).enqueue(object : Callback<AppleResponse> {

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



    override fun uploadTracks(text: String) {
        retrofit.search(text)
    }


    override fun clear(): ArrayList<TrackDTO> = ArrayList()
}