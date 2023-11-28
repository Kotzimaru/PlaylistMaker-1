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

    override fun isTrackAlreadyExists(playlist: PlaylistModel, track: TrackModel): Boolean {
        return playlist.trackList.contains(track)
    }

    override suspend fun addTrackToPlaylist(playlist: PlaylistModel, track: TrackModel) {
        var updatedPlaylist: PlaylistModel =
            playlist.copy(trackList = listOf(track) + playlist.trackList)
        updatedPlaylist = updatedPlaylist.copy(tracksCount = updatedPlaylist.trackList.size)

        repository.updateTracks(updatedPlaylist)
    }

    override suspend fun deleteTrack(playlist: PlaylistModel, track: TrackModel): PlaylistModel {
        var updatedPlaylist: PlaylistModel = playlist.copy(trackList = playlist.trackList - track)
        updatedPlaylist = updatedPlaylist.copy(tracksCount = updatedPlaylist.trackList.size)
        repository.updateTracks(updatedPlaylist)

        return playlist
    }

    override suspend fun deletePlaylist(playlist: PlaylistModel) {
        repository.deletePlaylist(playlist)
    }

    override fun getPlaylist(id: Int): Flow<PlaylistModel> {
        return repository.getPlaylistById(id)
    }
}