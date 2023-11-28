package com.example.playlistmaker1.media.data.converter


import com.example.playlistmaker1.search.data.TrackModelDto
import com.example.playlistmaker1.search.domain.api.TrackModel
import com.example.playlistmaker1.media.data.database.entity.TrackEntity
import java.util.Date

class TrackModelConverter {

    fun map(track: TrackModelDto): TrackModel =
        with(track) {
            TrackModel(
                trackId = trackId ?: "",
                trackName = trackName ?: "",
                artistName = artistName ?: "",
                trackTimeMillis = trackTimeMillis ?: 0,
                artworkUrl60 = artworkUrl60 ?: "",
                artworkUrl100 = artworkUrl100 ?: "",
                collectionName = collectionName ?: "",
                country = country ?: "",
                primaryGenreName = primaryGenreName ?: "",
                releaseDate = releaseDate ?: "",
                previewUrl = previewUrl ?: "",
            )
        }

    fun map(track: TrackModel): TrackModelDto =
        with(track) {
            TrackModelDto(
                trackId = trackId,
                trackName = trackName,
                artistName = artistName,
                trackTimeMillis = trackTimeMillis,
                artworkUrl60 = artworkUrl60,
                artworkUrl100 = artworkUrl100,
                collectionName = collectionName,
                country = country,
                primaryGenreName = primaryGenreName,
                releaseDate = releaseDate,
                previewUrl = previewUrl,
            )
        }

    fun map(track: TrackEntity): TrackModel =
        with(track) {
            TrackModel(
                trackId = trackId,
                trackName = trackName,
                artistName = artistName,
                trackTimeMillis = trackTimeMillis,
                artworkUrl60 = artworkUrl60,
                artworkUrl100 = artworkUrl100,
                collectionName = collectionName,
                country = country,
                primaryGenreName = primaryGenreName,
                releaseDate = releaseDate,
                previewUrl = previewUrl,
            )
        }


    fun mapToEntity(track: TrackModel): TrackEntity =
        with(track) {
            TrackEntity(
                trackId = trackId,
                trackName = trackName,
                artistName = artistName,
                trackTimeMillis = trackTimeMillis,
                artworkUrl60 = artworkUrl60,
                artworkUrl100 = artworkUrl100,
                collectionName = collectionName,
                country = country,
                primaryGenreName = primaryGenreName,
                releaseDate = releaseDate,
                previewUrl = previewUrl,
                saveDate = Date()
            )
        }
}