package com.example.playlistmaker1.playlist_menu.domain.impl

import com.example.playlistmaker1.playlist_menu.domain.api.PlaylistDurationCalculator
import com.example.playlistmaker1.search.domain.api.TrackModel

class PlaylistDurationCalculatorImpl : PlaylistDurationCalculator {
    override fun getTracksDuration(trackList: List<TrackModel>): Int {
        
        return trackList.sumOf { it.trackTimeMillis } / MILLIS_IN_MINUTES
    }
    
    companion object {
        private const val MILLIS_IN_MINUTES = 60000
    }
}