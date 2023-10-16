package com.example.playlistmaker1.search.data.storage.sharedprefs

import TracksStorage
import android.content.SharedPreferences
import com.example.playlistmaker1.player.data.TrackDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefsTracksStorage(private val sharedPreferences: SharedPreferences) : TracksStorage {
    
    private val gson = Gson()
    
    override fun saveHistory(historyList: List<TrackDTO>) {
        val json = gson.toJson(historyList)
        
        sharedPreferences
            .edit()
            .putString(HISTORY_LIST_KEY, json)
            .apply()
    }
    
    override fun readHistory(): List<TrackDTO> {
        val json = sharedPreferences.getString(HISTORY_LIST_KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<TrackDTO>>() {}.type
        return gson.fromJson(json, type)
    }
    
    companion object {
        private const val HISTORY_LIST_KEY = "history_preferences"
    }
}