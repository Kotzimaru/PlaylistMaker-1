package com.example.playlistmaker1

import com.example.playlistmaker1.player.domain.TrackDTO
import com.google.ar.core.Track

interface Serializator {

    fun jsonToTrack(textJSON: String?) : TrackDTO

    fun trackToJSON(track: Track): String?

}