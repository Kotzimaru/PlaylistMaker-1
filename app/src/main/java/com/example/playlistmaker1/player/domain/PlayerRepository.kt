package com.example.playlistmaker1.player.domain


interface PlayerRepository {

    fun start()
    fun pause()
    fun getCurrentTime(): Int
    fun preparePlayer(track: TrackDTO)
    fun onCompletionListener()
    fun releasePlayer()
}