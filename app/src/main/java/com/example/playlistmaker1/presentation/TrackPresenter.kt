package com.example.playlistmaker1

import com.example.playlistmaker1.domain.api.TracksInteractor
import com.example.playlistmaker1.domain.models.Track
import com.example.playlistmaker1.presentation.TrackView

class TrackPresenter(
    private var view: TrackView?,
    private val trackId: String,
    private val tracksInteractor: TracksInteractor,
) {
    private val trackConsumer: TracksInteractor.TrackInfoConsumer =
        object : TracksInteractor.TrackInfoConsumer {
            override fun consume(track: Track) {
                view?.drawTrack(track)
            }
        }

    init {
        tracksInteractor.getTrackInfo(trackId, trackConsumer)
    }

    fun likeTrack() {
        tracksInteractor.likeTrack(trackId, trackConsumer)
    }

    fun unlikeTrack() {
        tracksInteractor.unlikeTrack(trackId, trackConsumer)
    }

    fun onViewDestroyed() {
        view = null
    }
}