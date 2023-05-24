package com.example.playlistmaker1.player.presentation

import android.os.Handler
import android.os.Looper
import com.example.playlistmaker1.R
import com.example.playlistmaker1.TrackDTO
import com.example.playlistmaker1.player.data.PlayerState
import com.example.playlistmaker1.player.data.PlayerState.Companion.CURRENT_TIME_ZERO
import com.example.playlistmaker1.player.data.PlayerState.Companion.RELOAD_PROGRESS
import com.example.playlistmaker1.player.data.PlayerState.Companion.STATE_PAUSED
import com.example.playlistmaker1.player.data.PlayerState.Companion.STATE_PLAYING
import com.example.playlistmaker1.player.data.PlayerState.Companion.STATE_PREPARED
import com.example.playlistmaker1.player.domain.PlayerInteractor
import com.example.playlistmaker1.player.domain.PlayerStateListener
import java.text.SimpleDateFormat
import java.util.*


private const val play = R.drawable.button_play
private const val pause = R.drawable.button_pause

class PlayerPresenter(private val view: PlayerView) : PlayerStateListener {

    private val interactor = PlayerInteractor(this)

    val mainHandler: Handler = Handler(Looper.getMainLooper())
    private var playerState = PlayerState.STATE_DEFAULT

    private val runThread = object : Runnable {
        override fun run() {
            val currentTime =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(interactor.getCurrentTime())
            view.setCurrentTime(currentTime)

            mainHandler.postDelayed(
                this,
                RELOAD_PROGRESS
            )
        }
    }

    fun onClickPlayAndPause() {
        playbackControl()
        currentTimeControl()
    }

    fun preparePlayer(track: TrackDTO) {
        interactor.preparePlayer(track)
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    fun startPlayer() {
        interactor.start()
        view.setImage(pause)
        playerState = STATE_PLAYING
    }

    fun pausePlayer() {
        interactor.pause()
        view.setImage(play)
        playerState = STATE_PAUSED
    }

    fun onCompletionListener() {
        interactor.onCompletionListener()
    }

    private fun currentTimeControl() {
        when (playerState) {
            STATE_PLAYING -> {
                mainHandler.postDelayed(
                    runThread,
                    RELOAD_PROGRESS
                )
            }
            STATE_PAUSED -> {
                mainHandler.removeCallbacks(runThread)
            }
        }
    }

    fun backButtonClicked() {
        view.goBack()
    }

    fun clearPlayer() {
        interactor.releasePlayer()
        mainHandler.removeCallbacks(runThread)
    }

    fun fixReleaseDate(string: String): String = string.removeRange(4 until string.length)

    fun millisFormat(track: TrackDTO): String =
        SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)

    override fun setStatePrepared() {
        view.setImage(play)
        playerState = STATE_PREPARED
    }

    override fun removeHandlersCallbacks() {
        mainHandler.removeCallbacks(runThread)
    }

    override fun setImagePlay() {
        view.setImage(play)
    }

    override fun setCurrentTimeZero() {
        view.setCurrentTime(CURRENT_TIME_ZERO)
    }
}