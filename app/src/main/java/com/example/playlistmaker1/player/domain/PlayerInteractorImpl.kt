package com.example.playlistmaker1.player.domain

import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.player.domain.api.PlayerInteractor
import com.example.playlistmaker1.player.domain.api.PlayerRepository


class PlayerInteractorImpl(
    private val playerRepository: PlayerRepository
) : PlayerInteractor {


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

    override fun setOnPreparedListener(listener: (Any) -> Unit) {
        playerRepository.setOnPreparedListener(listener)

    }

    override fun setOnCompletionListener(listener: (Any) -> Unit) {
        playerRepository.setOnCompletionListener(listener)
    }

}