package com.example.playlistmaker1.playlist_creator.domain.models

import com.example.playlistmaker1.search.domain.api.TrackModel

data class PlaylistModel(
    val id: Int,
    val coverImageUrl: String,
    val playlistName: String,
    val playlistDescription:String,
    var trackList: List<TrackModel>,
    var tracksCount: Int,
)