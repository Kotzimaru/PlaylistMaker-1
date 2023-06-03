package com.example.playlistmaker1.player.domain

class PlayerInteractor(private val playerRepository: PlayerRepository) {

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

}