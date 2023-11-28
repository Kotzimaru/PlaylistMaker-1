package com.example.playlistmaker1.media.data.converter

import com.example.playlistmaker1.media.data.database.entity.PlaylistEntity
import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date

class PlaylistModelConverter {

    fun map(playlist: PlaylistModel): PlaylistEntity {
        return with(playlist) {
            PlaylistEntity(
                id = id,
                playlistName = playlistName,
                playlistDescription = playlistDescription,
                imageUrl = coverImageUrl,
                trackList = Json.encodeToString(trackList),
                countTracks = tracksCount,
                saveDate = Date(),
            )
        }
    }

    fun map(playlist: PlaylistEntity?): PlaylistModel {
        return if (playlist != null) {
            with(playlist) {
                PlaylistModel(
                    id = id,
                    playlistName = playlistName,
                    playlistDescription = playlistDescription,
                    coverImageUrl = imageUrl,
                    trackList = Json.decodeFromString(trackList),
                    tracksCount = countTracks,
                )
            }
        } else PlaylistModel.emptyPlaylist
    }
}