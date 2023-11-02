package com.example.playlistmaker1.search.data.storage.sharedprefs

import com.example.playlistmaker1.search.data.TrackDTO

interface TracksStorage {
    fun saveHistory(historyList: List<TrackDTO>)
    fun readHistory(): List<TrackDTO>
}