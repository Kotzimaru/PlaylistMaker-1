package com.example.playlistmaker1.playlist_menu.ui.models

import com.example.playlistmaker1.media.ui.models.ScreenState
import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel

sealed interface PlaylistMenuState {
    object DefaultState : PlaylistMenuState
    object EmptyShare : PlaylistMenuState
    data class Content(val content: PlaylistModel, val bottomListState: ScreenState) :
        PlaylistMenuState
    class Share(val playlist: PlaylistModel) : PlaylistMenuState
}