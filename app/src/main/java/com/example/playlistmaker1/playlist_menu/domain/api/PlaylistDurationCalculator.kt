package com.example.playlistmaker1.playlist_menu.domain.api

import com.example.playlistmaker1.search.domain.api.TrackModel

interface PlaylistDurationCalculator {
    
    fun getTracksDuration(trackList: List<TrackModel>): Int
}