package com.example.playlistmaker1.search.domain

import com.example.playlistmaker1.player.data.TrackDTO

interface Uploader {
    suspend fun evaluateRequest(text: String): ArrayList<TrackDTO>?
}