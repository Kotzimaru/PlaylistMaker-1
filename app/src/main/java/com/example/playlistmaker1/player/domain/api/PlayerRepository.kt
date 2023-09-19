package com.example.playlistmaker1.player.domain.api

import com.example.playlistmaker1.player.data.TrackDTO


interface PlayerRepository {
    fun start()
    fun pause()
    fun getCurrentTime(): Int
    fun preparePlayer(track: TrackDTO)
    fun releasePlayer()
    fun setOnPreparedListener(listener: (Any) -> Unit)
    fun setOnCompletionListener(listener: (Any) -> Unit)
}
