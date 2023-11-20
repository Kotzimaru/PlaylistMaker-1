package com.example.playlistmaker1.playlist_menu.ui.models

import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel

sealed interface ShareState {
    object Empty : ShareState
    class Content(val playlist: PlaylistModel) : ShareState
}