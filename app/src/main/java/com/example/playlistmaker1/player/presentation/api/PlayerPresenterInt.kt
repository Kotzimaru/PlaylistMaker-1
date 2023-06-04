package com.example.playlistmaker1.player.presentation.api

import com.example.playlistmaker1.player.domain.TrackDTO

interface PlayerPresenterInt {

    fun clearPlayer()

    fun preparePlayer(track: TrackDTO)

    fun onCompletionListener()

    fun setStatePrepared()

    fun removeHandlersCallbacks()

    fun setImagePlay()

    fun setCurrentTimeZero()
}