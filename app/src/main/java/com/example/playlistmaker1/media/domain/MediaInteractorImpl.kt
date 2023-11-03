package com.example.playlistmaker1.media.domain

import com.example.playlistmaker1.media.domain.api.MediaInteractor
import com.example.playlistmaker1.media.domain.api.MediaRepository
import com.example.playlistmaker1.search.domain.api.TrackModel
import kotlinx.coroutines.flow.Flow

class MediaInteractorImpl(
    private val repository: MediaRepository,
) : MediaInteractor {

    override suspend fun likeTrack(track: TrackModel) {
        repository.saveTrack(track)
    }

    override suspend fun unLikeTrack(track: TrackModel) {
        repository.deleteTrack(track)
    }

    override fun getSelectedTracks(): Flow<List<TrackModel>> = repository.getSelectedTracks()

    override fun isFavorite(id: String): Flow<Boolean> = repository.isFavorite(id)
}