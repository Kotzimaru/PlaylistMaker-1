package com.example.playlistmaker1.search.data


import com.example.playlistmaker1.player.data.TrackDTO

class TrackModelConverter {
    
    fun mapDtoToModel(list: List<TrackDTO>): List<TrackDTO> = list.map {
        TrackDTO(
            trackId = it.trackId ?: "",
            trackName = it.trackName ?: "",
            artistName = it.artistName ?: "",
            trackTimeMillis = it.trackTimeMillis ?: 0,
            artworkUrl100 = it.artworkUrl100 ?: "",
            collectionName = it.collectionName ?: "",
            country = it.country ?: "",
            primaryGenreName = it.primaryGenreName ?: "",
            releaseDate = it.releaseDate ?: "",
            previewUrl = it.previewUrl ?: "",
        )
    }
    
     fun mapModelToDto(list: List<TrackDTO>): List<TrackDTO> = list.map {
         TrackDTO(
            trackId = it.trackId,
            trackName = it.trackName,
            artistName = it.artistName,
            trackTimeMillis = it.trackTimeMillis,
            artworkUrl100 = it.artworkUrl100,
            collectionName = it.collectionName,
            country = it.country,
            primaryGenreName = it.primaryGenreName,
            releaseDate = it.releaseDate,
            previewUrl = it.previewUrl,
        )
    }
}