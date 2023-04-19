package com.example.playlistmaker1

import com.google.ar.core.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SerializatorTrack : Serializator {

    override fun trackToJSON(track: Track): String? {
        return Gson().toJson(track)
    }

    override fun jsonToTrack(textJSON: String?) : TrackDTO {
        return Gson().fromJson(textJSON, object : TypeToken<Track?>() {}.type)
    }
}