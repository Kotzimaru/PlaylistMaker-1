package com.example.playlistmaker1.media.domain.api

import com.example.playlistmaker1.search.domain.api.TrackModel
import kotlinx.coroutines.flow.Flow

interface MediaInteractor {
    suspend fun likeTrack(track: TrackModel)
    suspend fun unLikeTrack(track: TrackModel)
    fun getSelectedTracks(): Flow<List<TrackModel>>
    fun isFavorite(id: String): Flow<Boolean>
}