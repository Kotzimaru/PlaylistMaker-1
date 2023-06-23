package com.example.playlistmaker1.player.ui.viewmodels


import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker1.creator.Creator
import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.player.domain.PlayerState
import com.example.playlistmaker1.player.domain.PlayerState.Companion.CURRENT_TIME_ZERO
import com.example.playlistmaker1.player.domain.StateMusicPlayer
import com.example.playlistmaker1.search.data.network.SearchSerializator
import java.text.SimpleDateFormat
import java.util.*

class PlayerViewModel(val text: String?) : ViewModel() {

    private val interactor = Creator.getPlayerInteractor()

    private var mainHandler: Handler
    //private var playerState = PlayerState.STATE_DEFAULT



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
        mainHandler = Handler(Looper.myLooper()!!)
    }

    private val runThread = object : Runnable {
        override fun run() {
            currentTime.value = SimpleDateFormat("mm:ss", Locale.getDefault()).format(interactor.getCurrentTime())
            mainHandler.postDelayed(
                this,
                PlayerState.RELOAD_PROGRESS
            )
        }
    }


    fun preparePlayer() {
        track.value?.let { interactor.preparePlayer(it) }
        interactor.setOnPreparedListener {
            //track.postValue(SearchSerializator().jsonToTrack(text))
            state.value = StateMusicPlayer.PREPARED
        }
        interactor.setOnCompletionListener {
            state.value = StateMusicPlayer.PREPARED
            //stopTimer()
            currentTime.value = CURRENT_TIME_ZERO
        }
    }

    private fun stopTimer() = mainHandler.removeCallbacks { runThread }


    /*private fun currentTimeControl() {
        when (state.value) {
            StateMusicPlayer.PLAYING -> {
                mainHandler.postDelayed(
                    runThread,
                    PlayerState.RELOAD_PROGRESS
                )
            }
            StateMusicPlayer.PAUSED -> {
                mainHandler.removeCallbacks(runThread)
            }
            else -> {}
        }
    }*/

    fun fixReleaseDate(string: String): String = string.removeRange(4 until string.length)

    fun playbackControl() {

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
        interactor.pause()
        state.value = StateMusicPlayer.PAUSED
        mainHandler.removeCallbacks(runThread)
    }

    private fun startPlayer() {
        interactor.start()
        state.value = StateMusicPlayer.PLAYING
        mainHandler.postDelayed({ runThread.run() }, PlayerState.RELOAD_PROGRESS)
    }

    override fun onCleared() {
        interactor.releasePlayer()
        mainHandler.removeCallbacks(runThread)



    }
    fun onDestroy(){
        stopTimer()
        interactor.releasePlayer()
    }
}