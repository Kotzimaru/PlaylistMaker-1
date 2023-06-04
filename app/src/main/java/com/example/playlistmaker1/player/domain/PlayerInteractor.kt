package com.example.playlistmaker1.player.domain

import com.example.playlistmaker1.player.creator.Creator
import com.example.playlistmaker1.player.domain.api.PlayerRepository
import com.example.playlistmaker1.player.presentation.api.PlayerPresenterInt


class PlayerInteractor(private val playerPresenterInt: PlayerPresenterInt): PlayerRepository {

    private val playerRepositoryImpl = Creator.getRepositoryImpl(this)


    override fun start() {
        playerRepositoryImpl.start()
    }

    override fun pause() {
        playerRepositoryImpl.pause()
    }

    override fun getCurrentTime(): Int {
        return playerRepositoryImpl.getCurrentTime()
    }

    override fun preparePlayer(track: TrackDTO) {
        playerRepositoryImpl.preparePlayer(track)
    }

    override fun onCompletionListener() {
        playerRepositoryImpl.onCompletionListener()
    }

    override fun releasePlayer() {
        playerRepositoryImpl.releasePlayer()
    }
    override fun setStatePrepared() {
        playerPresenterInt.setStatePrepared()
    }

    override fun removeHandlersCallbacks() {
        playerPresenterInt.removeHandlersCallbacks()

    }

    override fun setImagePlay() {
        playerPresenterInt.setImagePlay()
    }

    override fun setCurrentTimeZero() {
        playerPresenterInt.setCurrentTimeZero()

    }
}