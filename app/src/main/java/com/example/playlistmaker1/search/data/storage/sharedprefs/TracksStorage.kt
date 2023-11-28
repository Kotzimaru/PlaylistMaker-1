package com.example.playlistmaker1.search.data.storage.sharedprefs

import com.example.playlistmaker1.search.data.TrackModelDto

interface TracksStorage {
    fun saveHistory(historyList: List<TrackModelDto>)
    fun readHistory(): List<TrackModelDto>
}