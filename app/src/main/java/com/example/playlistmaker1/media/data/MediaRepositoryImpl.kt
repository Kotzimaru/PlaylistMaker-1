package com.example.playlistmaker1.media.data

import com.example.playlistmaker1.media.domain.api.MediaRepository
import com.example.playlistmaker1.search.domain.api.TrackModel
import com.example.playlistmaker1.media.data.database.LocalDatabase
import com.example.playlistmaker1.media.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MediaRepositoryImpl(
    private val database: LocalDatabase,
    private val converter: TrackModelConverter,
) : MediaRepository {
    override suspend fun saveTrack(track: TrackModel) {
        database
            .dao()
            .insertTrack(converter.mapToEntity(track))
    }

    override suspend fun deleteTrack(track: TrackModel) {
        database
            .dao()
            .deleteTrack(converter.mapToEntity(track))
    }

    override fun getSelectedTracks(): Flow<List<TrackModel>> = database
        .dao()
        .getFavoriteTracks()
        .map { convertFromTrackEntity(it) }

    override fun isFavorite(id: String): Flow<Boolean> = database
        .dao()
        .isFavorite(id)


    private fun convertFromTrackEntity(tracks: List<TrackEntity>): List<TrackModel> =
        tracks.map { converter.map(it) }
}
