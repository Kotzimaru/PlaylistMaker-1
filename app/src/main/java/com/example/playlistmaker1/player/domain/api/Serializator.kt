package com.example.playlistmaker1.player.domain.api

import com.example.playlistmaker1.player.domain.TrackDTO

interface Serializator {

    fun jsonToTrack(textJSON: String?) : TrackDTO

    fun trackToJSON(track: TrackDTO): String?

    fun tracksToJson(tracks: ArrayList<TrackDTO>?) : String?

    fun jsonToTracks(textJSON: String?) : ArrayList<TrackDTO>

}