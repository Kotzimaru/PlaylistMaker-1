package com.example.playlistmaker1.player.data

interface PlayerStateListener {
    fun setStatePrepared()
    fun removeHandlersCallbacks()
    fun setImagePlay()
    fun setCurrentTimeZero()
}