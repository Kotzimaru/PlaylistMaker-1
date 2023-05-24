package com.example.playlistmaker1.player.data

interface MediaPlayerListener {
    fun setStatePrepared()
    fun removeHandlersCallbacks()
    fun setImagePlay()
    fun setCurrentTimeZero()
}