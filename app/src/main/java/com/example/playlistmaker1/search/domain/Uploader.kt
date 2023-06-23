package com.example.playlistmaker1.search.domain

import com.example.playlistmaker1.player.data.TrackDTO

interface Uploader {
    fun getTracks(tracks: ArrayList<TrackDTO>?)
}