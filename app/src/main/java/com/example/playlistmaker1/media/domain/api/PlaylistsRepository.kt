package com.example.playlistmaker1.media.domain.api


import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {

    suspend fun createPlaylist(playlist: PlaylistModel)

    suspend fun deletePlaylist(playlist: PlaylistModel)

    suspend fun updateTracks(playlist: PlaylistModel)

    fun getSavedPlaylists(): Flow<List<PlaylistModel>>

    fun getPlaylistById(id: Int): Flow<PlaylistModel>
}