package com.example.playlistmaker1.player.ui.viewmodels


import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.player.domain.PlayerState
import com.example.playlistmaker1.player.domain.PlayerState.Companion.CURRENT_TIME_ZERO
import com.example.playlistmaker1.player.domain.StateMusicPlayer
import com.example.playlistmaker1.player.domain.api.PlayerInteractor
import com.example.playlistmaker1.search.data.network.SearchSerializator
import java.text.SimpleDateFormat
import java.util.*

class PlayerViewModel(
    val text: String?,
    private val playerInteractor: PlayerInteractor,
    private val mainHandler: Handler,
) : ViewModel() {

    private var track = MutableLiveData<TrackDTO>()
    private var currentTime = MutableLiveData<String>()
    private var state = MutableLiveData<StateMusicPlayer>()

    fun getTrackData(): LiveData<TrackDTO> = track
    fun getTimerTextData(): LiveData<String> = currentTime
    fun getStateData(): LiveData<StateMusicPlayer> = state

    init {
        track.value = SearchSerializator().jsonToTrack(text)
        currentTime.value = CURRENT_TIME_ZERO
        state.value = StateMusicPlayer.DEFAULT

    }

    private val runThread = object : Runnable {
        override fun run() {
            currentTime.value = SimpleDateFormat(
                "mm:ss",
                Locale.getDefault()
            ).format(playerInteractor.getCurrentTime())
            mainHandler.postDelayed(
                this,
                PlayerState.RELOAD_PROGRESS
            )
        }
    }


    fun preparePlayer() {
        track.value?.let { playerInteractor.preparePlayer(it) }
        playerInteractor.setOnPreparedListener {
            //track.postValue(SearchSerializator().jsonToTrack(text))
            state.value = StateMusicPlayer.PREPARED
        }
        playerInteractor.setOnCompletionListener {
            mainHandler.removeCallbacks(runThread)
            state.value = StateMusicPlayer.PREPARED
            stopTimer()

        }
    }

    private fun stopTimer() {
        mainHandler.removeCallbacks ( runThread ).let { currentTime.value = CURRENT_TIME_ZERO }
    }

    fun fixReleaseDate(string: String): String = string.removeRange(4 until string.length)

    fun playbackControl() {

        when (state.value) {
            StateMusicPlayer.PLAYING -> {
                pausePlayer()
            }
            StateMusicPlayer.PAUSED, StateMusicPlayer.PREPARED, StateMusicPlayer.DEFAULT -> {
                startPlayer()
            }
            else -> {}
        }
    }

    fun pausePlayer() {
        playerInteractor.pause()
        state.value = StateMusicPlayer.PAUSED
        mainHandler.removeCallbacks(runThread)
    }

    private fun startPlayer() {
        playerInteractor.start()
        state.value = StateMusicPlayer.PLAYING
        mainHandler.postDelayed({ runThread.run() }, PlayerState.RELOAD_PROGRESS)
    }

    override fun onCleared() {
        playerInteractor.releasePlayer()
        mainHandler.removeCallbacks(runThread)


    }

    fun onDestroy() {
        stopTimer()
        playerInteractor.releasePlayer()
    }
}