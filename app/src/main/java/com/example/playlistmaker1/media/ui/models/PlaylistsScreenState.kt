package com.example.playlistmaker1.media.ui.models

import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel

sealed class PlaylistsScreenState {
    
    object Empty : PlaylistsScreenState()
    
    class Content(val playlists: List<PlaylistModel>) : PlaylistsScreenState()
}