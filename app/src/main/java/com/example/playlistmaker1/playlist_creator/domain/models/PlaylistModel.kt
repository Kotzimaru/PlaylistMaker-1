package com.example.playlistmaker1.playlist_creator.domain.models

import com.example.playlistmaker1.search.domain.api.TrackModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistModel(
    val id: Int,
    val coverImageUrl: String,
    val playlistName: String,
    val playlistDescription: String,
    val trackList: List<TrackModel>,
    val tracksCount: Int,
) {
    companion object {
        val emptyPlaylist = PlaylistModel(
            id = 0,
            coverImageUrl = "",
            playlistName = "",
            playlistDescription = "",
            trackList = emptyList(),
            tracksCount = 0,
        )
    }
}