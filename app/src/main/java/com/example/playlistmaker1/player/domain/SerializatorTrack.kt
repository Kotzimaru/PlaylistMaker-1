package com.example.playlistmaker1.player.domain

import com.example.playlistmaker1.player.domain.api.Serializator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SerializatorTrack : Serializator {

    override fun trackToJSON(track: TrackDTO): String? {
        return Gson().toJson(track)
    }

    override fun jsonToTrack(textJSON: String?) : TrackDTO {
        return Gson().fromJson(textJSON, object : TypeToken<TrackDTO?>() {}.type)
    }

    override fun tracksToJson(tracks: ArrayList<TrackDTO>?): String? {
        return Gson().toJson(tracks)
    }

    override fun jsonToTracks(textJSON: String?): ArrayList<TrackDTO> {
        return Gson().fromJson(textJSON, object : TypeToken<ArrayList<TrackDTO>?>() {}.type)
    }
}