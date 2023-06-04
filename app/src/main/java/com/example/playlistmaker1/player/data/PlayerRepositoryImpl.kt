package com.example.playlistmaker1.player.data

import android.media.MediaPlayer
import com.example.playlistmaker1.player.domain.api.PlayerRepository
import com.example.playlistmaker1.player.domain.TrackDTO

class PlayerRepositoryImpl(private val playerRepository: PlayerRepository) {

    private val mediaPlayer = MediaPlayer()

    fun start() {
        mediaPlayer.start()
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun getCurrentTime(): Int {
        return mediaPlayer.currentPosition
    }

    fun preparePlayer(track: TrackDTO) {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener() {
            playerRepository.setStatePrepared()
        }
        mediaPlayer.setOnCompletionListener {
            playerRepository.setStatePrepared()
        }
    }

    fun onCompletionListener() {
        mediaPlayer.setOnCompletionListener {
            playerRepository.setStatePrepared()
            playerRepository.removeHandlersCallbacks()
            playerRepository.setImagePlay()
            playerRepository.setCurrentTimeZero()
        }
    }

    fun releasePlayer() {
        mediaPlayer.release()
    }



}