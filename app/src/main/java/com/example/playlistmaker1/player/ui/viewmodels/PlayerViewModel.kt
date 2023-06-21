package com.example.playlistmaker1.player.ui.viewmodels

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.entities.FormatterTime
import com.example.playlistmaker1.player.domain.SerializatorTrack
import com.example.playlistmaker1.player.domain.TrackDTO
import com.example.playlistmaker1.player.domain.models.StateMusicPlayer



class PlayerViewModel(val text: String?): ViewModel() {

    companion object {
        private const val REFRESH_TIME = 1000L
        private const val NULL_TIMER = "00:00"
    }

    private var handler: Handler

    private var track = MutableLiveData<TrackDTO>()
    private var timerText = MutableLiveData<String>()
    private var state = MutableLiveData<StateMusicPlayer>()

    fun getTrackData(): LiveData<TrackDTO> = track
    fun getTimerTextData(): LiveData<String> = timerText

    private val mediaPlayer = MediaPlayer()

    init {
        track.value = SerializatorTrack().jsonToTrack(text)
        timerText.value = NULL_TIMER
        state.value = StateMusicPlayer.DEFAULT
        handler = Handler(Looper.myLooper()!!)
    }

    fun preparePlayer() {
        mediaPlayer.setDataSource(track.value?.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnCompletionListener {
            state.value = StateMusicPlayer.PREPARED
            stopTimer()
            timerText.value = NULL_TIMER
        }
        mediaPlayer.setOnPreparedListener { state.value = StateMusicPlayer.PREPARED }
    }

    private fun stopTimer() = handler.removeCallbacks { refreshTimer() }

    private fun refreshTimer() {
        if (state.value != StateMusicPlayer.PAUSED) {
            timerText.postValue(FormatterTime.formatTime(mediaPlayer.currentPosition.toString()))
        }
        handler.postDelayed({ refreshTimer() }, REFRESH_TIME)
    }

    fun controlPlayer() {
        when (state.value) {
            StateMusicPlayer.PLAYING -> {
                pausePlayer()
            }
            StateMusicPlayer.PAUSED, StateMusicPlayer.PREPARED, StateMusicPlayer.DEFAULT -> {
                startPlayer()
            }
            null -> TODO()
        }
    }

    fun pausePlayer() {
        mediaPlayer.pause()
        state.value = StateMusicPlayer.PAUSED
        stopTimer()
    }

    private fun startPlayer() {
        mediaPlayer.start()
        state.value = StateMusicPlayer.PLAYING
        handler.postDelayed({ refreshTimer() }, REFRESH_TIME)
    }

    fun onDestroy() {
        stopTimer()
        mediaPlayer.release()
    }
}
