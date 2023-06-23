package com.example.playlistmaker1.player.domain.api

import com.example.playlistmaker1.player.data.TrackDTO

interface PlayerInteractor {
    fun preparePlayer(track: TrackDTO)
    fun getCurrentTime(): Int
    fun start()
    fun pause()
    fun releasePlayer()
    fun setOnPreparedListener(listener: (Any) -> Unit)
    fun setOnCompletionListener(listener: (Any) -> Unit)
}