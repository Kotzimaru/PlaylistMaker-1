package com.example.playlistmaker1.player.domain

interface PlayerStateListener {
    fun setStatePrepared()
    fun removeHandlersCallbacks()
    fun setImagePlay()
    fun setCurrentTimeZero()
}