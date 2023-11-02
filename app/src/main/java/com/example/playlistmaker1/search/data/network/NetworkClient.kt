package com.example.playlistmaker1.search.data.network

interface NetworkClient {
    suspend fun doRequest(query: String): Response
}