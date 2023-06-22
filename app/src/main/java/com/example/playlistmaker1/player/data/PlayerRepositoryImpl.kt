package com.example.playlistmaker1.player.data

import android.media.MediaPlayer
import com.example.playlistmaker1.player.domain.api.PlayerRepository
import com.example.playlistmaker1.player.domain.TrackDTO

class PlayerRepositoryImpl(): PlayerRepository {

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

    }
    override fun releasePlayer() {
        mediaPlayer.release()
    }

    override fun setListeners(
        setOnPreparedListener: () -> Unit,
        setOnCompletionListener: () -> Unit
    ) {
        mediaPlayer.setOnPreparedListener() {
            setOnPreparedListener.invoke()
        }
        mediaPlayer.setOnCompletionListener {
            setOnCompletionListener.invoke()
        }
    }
}