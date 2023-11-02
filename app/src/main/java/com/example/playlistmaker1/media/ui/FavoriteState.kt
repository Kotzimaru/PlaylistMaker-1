package com.example.playlistmaker1.media.ui

import com.example.playlistmaker1.search.domain.api.TrackModel
sealed interface FavoriteState {
    object Empty : FavoriteState
    
    data class SelectedTracks(
        val trackList: List<TrackModel>,
    ) : FavoriteState
}