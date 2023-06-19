package com.example.playlistmaker1.search.domain

import android.content.SharedPreferences
import com.example.playlistmaker1.player.domain.SerializatorTrack
import com.example.playlistmaker1.player.domain.TrackDTO
import com.example.playlistmaker1.search.data.AppleApi

class SearchInteractor(private val sharedPreferences: SharedPreferences) {

    companion object {
        val serializator = SerializatorTrack()
        val api = AppleApi()
    }

    fun uploadTracks(text: String, uploader: Uploader) {
        api.evaluateRequest(text, uploader)
    }


    fun getHistory(): ArrayList<TrackDTO> {
        val stringHistory = sharedPreferences?.getString("tracksHistory", "")

        if (stringHistory?.isEmpty() == true) return ArrayList()

        return serializator.jsonToTracks(stringHistory)
    }

    fun setHistory(trackList: ArrayList<TrackDTO>?) {
        sharedPreferences?.edit()?.putString(
            "tracksHistory", serializator.tracksToJson(
                trackList,
            )
        )
            ?.apply()
    }

    fun clear(): ArrayList<TrackDTO> = ArrayList()

    fun removeTrack(trackList: ArrayList<TrackDTO>, track: TrackDTO): ArrayList<TrackDTO> {

        trackList.remove(track)
        trackList.add(0, track)

        if (trackList.size!! > 10) {
            trackList.removeLast()
        }
        setHistory(trackList)
        return trackList
    }

}