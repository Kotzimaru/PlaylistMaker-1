package com.example.playlistmaker1.creator

import com.example.playlistmaker1.data.TracksRepositoryImpl
import com.example.playlistmaker1.data.network.NetworkClientImpl
import com.example.playlistmaker1.domain.impl.TracksInteractorImpl
import com.example.playlistmaker1.presentation.track.TrackPresenter
import com.example.playlistmaker1.presentation.track.TrackView

object Creator {
    private fun getRepository(): TracksRepositoryImpl {
        return TracksRepositoryImpl(NetworkClientImpl())
    }

    fun providePresenter(view: TrackView, trackId: String): TrackPresenter {
        return TrackPresenter(
            view = view,
            trackId = trackId,
            tracksInteractor = TracksInteractorImpl(getRepository()),
        )
    }
}