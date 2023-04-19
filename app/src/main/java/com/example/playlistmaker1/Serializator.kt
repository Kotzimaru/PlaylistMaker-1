package com.example.playlistmaker1

import com.google.ar.core.Track

interface Serializator {

    fun jsonToTrack(textJSON: String?) : TrackDTO

    fun trackToJSON(track: Track): String?

}