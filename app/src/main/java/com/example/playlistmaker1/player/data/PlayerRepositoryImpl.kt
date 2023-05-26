package com.example.playlistmaker1.player.data

import android.media.MediaPlayer
import com.example.playlistmaker1.player.domain.PlayerRepository
import com.example.playlistmaker1.player.domain.TrackDTO

class PlayerRepositoryImpl(private val mediaPlayerListener: MediaPlayerListener) : PlayerRepository {

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
            mediaPlayerListener.setStatePrepared()
        }
        mediaPlayer.setOnCompletionListener {
            mediaPlayerListener.setStatePrepared()
        }
    }

    override fun onCompletionListener() {
        mediaPlayer.setOnCompletionListener {
            mediaPlayerListener.setStatePrepared()
            mediaPlayerListener.removeHandlersCallbacks()
            mediaPlayerListener.setImagePlay()
            mediaPlayerListener.setCurrentTimeZero()
        }
    }

    override fun releasePlayer() {
        mediaPlayer.release()
    }



}