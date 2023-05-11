package com.example.playlistmaker1.presentation

import com.example.playlistmaker1.domain.models.Track

interface TrackView {
    fun updateTrackLiked(liked: Boolean)
    fun drawTrack(track: Track)
}