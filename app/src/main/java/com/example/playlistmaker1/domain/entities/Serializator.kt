package com.example.playlistmaker1.domain.entities

import com.example.playlistmaker1.data.dto.TrackDTO
import com.google.ar.core.Track

interface Serializator {

    fun jsonToTrack(textJSON: String?) : TrackDTO

    fun trackToJSON(track: Track): String?

}