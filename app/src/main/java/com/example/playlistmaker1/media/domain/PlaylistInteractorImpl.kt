package com.example.playlistmaker1.media.domain

import com.example.playlistmaker1.media.domain.api.PlaylistsInteractor
import com.example.playlistmaker1.media.domain.api.PlaylistsRepository
import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel
import com.example.playlistmaker1.search.domain.api.TrackModel
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val repository: PlaylistsRepository,
) : PlaylistsInteractor {
    
    override fun getPlaylists(): Flow<List<PlaylistModel>> {
        return repository.getSavedPlaylists()
    }
    
    override fun isTrackAlreadyExists(playlist: PlaylistModel, track: TrackModel) =
        playlist.trackList.contains(track)
    
    override suspend fun addTrackToPlaylist(playlist: PlaylistModel, track: TrackModel) {
        playlist.trackList = playlist.trackList + track
        playlist.tracksCount = playlist.trackList.size
        repository.updateTracks(playlist)
    }
}