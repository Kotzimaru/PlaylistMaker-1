package com.example.playlistmaker1.player.presentation.api

import com.example.playlistmaker1.player.domain.TrackDTO

interface PlayerInteractor {
    fun preparePlayer(track: TrackDTO)
    fun getCurrentTime(): Int
    fun start()
    fun pause()
    fun releasePlayer()
    fun setListeners(setOnPreparedListener: (()->Unit), setOnCompletionListener: (()->Unit))
}