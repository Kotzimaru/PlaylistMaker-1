package com.example.playlistmaker1.search.data

import android.content.SharedPreferences
import com.example.playlistmaker1.player.domain.TrackDTO
import com.example.playlistmaker1.search.domain.SearchHistory
import com.google.gson.Gson

class SearchHistoryImpl (sharedPreferences: SharedPreferences): SearchHistory {

    private val sharedPrefs: SharedPreferences

    init {
        sharedPrefs = sharedPreferences
    }

    override fun get(): Array<TrackDTO>{
        val saveJson = sharedPrefs.getString(KEY_LIST_TRACKS, "[ ]")
        return Gson().fromJson(saveJson, Array<TrackDTO>::class.java)
    }

    override fun add(track: TrackDTO) {
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