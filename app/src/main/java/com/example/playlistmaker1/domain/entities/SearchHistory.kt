package com.example.playlistmaker1.domain.entities

import android.content.SharedPreferences
import com.example.playlistmaker1.data.dto.TrackDTO
import com.google.gson.Gson

class SearchHistory (sharedPreferences: SharedPreferences) {

    private val sharedPrefs: SharedPreferences

    init {
        sharedPrefs = sharedPreferences
    }

    fun get(): Array<TrackDTO>{
        val saveJson = sharedPrefs.getString(KEY_LIST_TRACKS, "[ ]")
        return Gson().fromJson(saveJson, Array<TrackDTO>::class.java)
    }

    fun add(track: TrackDTO) {
        val listObjects = get().filter { it.trackId != track.trackId }.toMutableList()
        if ( listObjects.size >= 10 ) {
            listObjects.removeLast()
        }
        listObjects.add(0, track)
        val json = Gson().toJson(listObjects)
        sharedPrefs.edit()
            .putString(KEY_LIST_TRACKS, json)
            .apply()
    }

    companion object {
        const val KEY_LIST_TRACKS = "tracks_history"
    }
}