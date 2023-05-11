package com.example.playlistmaker1.domain.api

import com.example.playlistmaker1.domain.models.Track

interface TracksInteractor {
    fun likeTrack(trackId: String, consumer: TrackInfoConsumer)
    fun unlikeTrack(trackId: String, consumer: TrackInfoConsumer)
    fun getTrackInfo(trackId: String, consumer: TrackInfoConsumer)

    interface TrackInfoConsumer {
        fun consume(track: Track)
    }
}