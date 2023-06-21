package com.example.playlistmaker1.search.domain

import com.example.playlistmaker1.player.domain.TrackDTO

interface Uploader {
    fun getTracks(tracks: ArrayList<TrackDTO>?)
}