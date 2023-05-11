package com.example.playlistmaker1.domain.models


data class Track(
    val trackId: String,
    val isLiked: Boolean,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String,
    val previewUrl: String,
    val collectionName: String,
    val country: String,
    val primaryGenreName: String,
    val releaseDate: String,)