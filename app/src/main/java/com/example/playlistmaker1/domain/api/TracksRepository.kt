package com.example.playlistmaker1.domain.api


import com.example.playlistmaker1.domain.models.Track

interface TracksRepository {
    fun likeTrack(trackId: String)
    fun unlikeTrack(trackId: String)
    fun getTrackForId(trackId: String): Track
}