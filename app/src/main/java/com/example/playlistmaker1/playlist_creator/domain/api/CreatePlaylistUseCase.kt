package com.example.playlistmaker1.playlist_creator.domain.api

import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel

interface CreatePlaylistUseCase {
    suspend fun create(playlist: PlaylistModel)
}