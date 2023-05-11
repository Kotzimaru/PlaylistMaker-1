package com.example.playlistmaker1.data.dto

data class Response(
    val resultCode: Int,
    val searchType: String,
    val expression: String,
    val results: ArrayList<TrackDTO>
)