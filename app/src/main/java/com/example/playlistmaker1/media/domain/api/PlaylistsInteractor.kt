package com.example.playlistmaker1.media.domain.api

import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel
import com.example.playlistmaker1.search.domain.api.TrackModel
import kotlinx.coroutines.flow.Flow

interface PlaylistsInteractor {

    fun getPlaylists(): Flow<List<PlaylistModel>>
    fun isTrackAlreadyExists(playlist: PlaylistModel, track: TrackModel): Boolean
    suspend fun addTrackToPlaylist(playlist: PlaylistModel, track: TrackModel)
    suspend fun deleteTrack(playlist: PlaylistModel, track: TrackModel): PlaylistModel
    suspend fun deletePlaylist(playlist: PlaylistModel)
    fun getPlaylist(id: Int): Flow<PlaylistModel>
}