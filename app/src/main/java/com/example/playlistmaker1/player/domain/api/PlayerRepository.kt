package com.example.playlistmaker1.player.domain.api

import com.example.playlistmaker1.player.domain.TrackDTO


interface PlayerRepository {

    fun start()
    fun pause()
    fun getCurrentTime(): Int
    fun preparePlayer(track: TrackDTO)
    fun onCompletionListener()
    fun releasePlayer()
    fun setStatePrepared()
    fun removeHandlersCallbacks()
    fun setImagePlay()
    fun setCurrentTimeZero()

}
