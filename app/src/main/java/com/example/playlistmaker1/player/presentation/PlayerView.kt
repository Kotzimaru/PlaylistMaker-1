package com.example.playlistmaker1.player.presentation

interface PlayerView {
    fun setImage(image: Int)
    fun setCurrentTime(time: String)
    fun goBack()
}