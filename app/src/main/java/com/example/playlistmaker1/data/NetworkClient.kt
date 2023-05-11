package com.example.playlistmaker1.data


interface NetworkClient {
    fun doRequest(dto: Any): Any
}