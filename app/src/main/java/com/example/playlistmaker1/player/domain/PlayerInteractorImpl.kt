package com.example.playlistmaker1.player.domain

import com.example.playlistmaker1.player.creator.Creator
import com.example.playlistmaker1.player.domain.api.PlayerRepository
import com.example.playlistmaker1.player.presentation.api.PlayerInteractor


class PlayerInteractorImpl(): PlayerInteractor {


    private val playerRepository = Creator.getRepositoryImpl()
    override fun setListeners(
        setOnPreparedListener: () -> Unit,
        setOnCompletionListener: () -> Unit
    ) {
        playerRepository.setListeners(setOnPreparedListener, setOnCompletionListener)
        }

    override fun start() {
        playerRepository.start()
    }

    override fun pause() {
        playerRepository.pause()
    }

    override fun getCurrentTime(): Int {
        return playerRepository.getCurrentTime()
    }

    override fun preparePlayer(track: TrackDTO) {
        playerRepository.preparePlayer(track)
    }

    override fun releasePlayer() {
        playerRepository.releasePlayer()
    }

}