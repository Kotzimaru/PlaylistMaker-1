package com.example.playlistmaker1.player.domain

import com.example.playlistmaker1.player.data.MediaPlayerListener
import com.example.playlistmaker1.player.data.PlayerRepositoryImpl

class PlayerInteractor(private val playerStateListener: PlayerStateListener) : MediaPlayerListener {

    private val playerRepository: PlayerRepository = PlayerRepositoryImpl(this)

    fun start() {
        playerRepository.start()
    }

    fun pause() {
        playerRepository.pause()
    }

    fun getCurrentTime(): Int {
        return playerRepository.getCurrentTime()
    }

    fun preparePlayer(track: TrackDTO) {
        playerRepository.preparePlayer(track)
    }

    fun onCompletionListener() {
        playerRepository.onCompletionListener()
    }

    fun releasePlayer() {
        playerRepository.releasePlayer()
    }

    override fun setStatePrepared() {
        playerStateListener.setStatePrepared()
    }

    override fun removeHandlersCallbacks() {
        playerStateListener.removeHandlersCallbacks()
    }

    override fun setImagePlay() {
        playerStateListener.setImagePlay()
    }

    override fun setCurrentTimeZero() {
        playerStateListener.setCurrentTimeZero()
    }

}