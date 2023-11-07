package com.example.playlistmaker1.playlist_creator.domain.impl

import com.example.playlistmaker1.media.domain.api.PlaylistsRepository
import com.example.playlistmaker1.playlist_creator.domain.api.CreatePlaylistUseCase
import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel

class CreatePlaylistUseCaseImpl(
    private val repository: PlaylistsRepository,
) : CreatePlaylistUseCase {
    
    override suspend fun create(playlist: PlaylistModel) {
        repository.createPlaylist(playlist)
    }
}