package com.example.playlistmaker1.player.data

import android.media.MediaPlayer
import com.example.playlistmaker1.player.domain.PlayerRepository
import com.example.playlistmaker1.player.domain.TrackDTO

class PlayerRepositoryImpl(private val playerStateListener: PlayerStateListener) : PlayerRepository {

    private val mediaPlayer = MediaPlayer()

    override fun start() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun getCurrentTime(): Int {
        return mediaPlayer.currentPosition
    }

    override fun preparePlayer(track: TrackDTO) {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener() {
            playerStateListener.setStatePrepared()
        }
        mediaPlayer.setOnCompletionListener {
            playerStateListener.setStatePrepared()
        }
    }

    override fun onCompletionListener() {
        mediaPlayer.setOnCompletionListener {
            playerStateListener.setStatePrepared()
            playerStateListener.removeHandlersCallbacks()
            playerStateListener.setImagePlay()
            playerStateListener.setCurrentTimeZero()
        }
    }

    override fun releasePlayer() {
        mediaPlayer.release()
    }



}