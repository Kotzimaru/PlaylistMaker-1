package com.example.playlistmaker1.search.data.network

import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.search.domain.Uploader

interface NetworkClient {
    suspend fun doRequest(query: String): Response
}