package com.example.playlistmaker1.player.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.player.domain.PlayerState
import com.example.playlistmaker1.player.domain.api.PlayerInteractor
import com.example.playlistmaker1.player.ui.PlayStatus
import com.example.playlistmaker1.search.domain.api.SearchInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val searchInteractor: SearchInteractor,
) : ViewModel() {

    private val playStatusLiveData = MutableLiveData<PlayStatus>()
    private val playProgress get() = playerInteractor.getPlayerPosition()
    private val playerState get() = playerInteractor.getPlayerState()

    private val trackUrl = getTrack().previewUrl

    private var progressTimerJob: Job? = null
    private var preparePlayerJob: Job? = null

    init {
        preparingPlayer(trackUrl)
    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.stopPlaying()
    }

    fun observePlayStatus(): LiveData<PlayStatus> = playStatusLiveData

    fun getTrack(): TrackDTO {
        return searchInteractor.historyList.first()
    }

    fun onViewPaused() {
        pausePlaying()
    }

    fun playButtonClicked() {
        when (playerState) {
            PlayerState.PLAYING -> pausePlaying()

            PlayerState.NOT_PREPARED -> {
                playStatusLiveData.value =
                    PlayStatus.Loading()
            }

            PlayerState.READY, PlayerState.PAUSED -> startPlaying()

            PlayerState.NOT_CONNECTED -> preparingPlayer(trackUrl)
        }
    }

    private fun preparingPlayer(url: String) {

        preparePlayerJob = viewModelScope.launch {
            playerInteractor
                .preparePlayer(url)
                .collect { playerState ->
                    processPlayStatus(playerState)
                }
        }
    }

    private fun processPlayStatus(playerState: PlayerState) {
        when (playerState) {
            PlayerState.READY -> { playStatusLiveData.value = PlayStatus.Ready() }
            else -> { playStatusLiveData.value = PlayStatus.NotConnected(playProgress = playProgress) }
        }
    }

    private fun startPlaying() {

        playerInteractor.startPlaying()
        progressTimerJob = viewModelScope.launch {
            do {
                playStatusLiveData.value = PlayStatus.Playing(playProgress = playProgress)
                delay(TIMER_DELAY)
            } while (playerState == PlayerState.PLAYING)

            if (playerState == PlayerState.READY) {
                pausePlaying()
            }
        }
    }


    private fun pausePlaying() {
        if (playerState == PlayerState.READY) {
            playStatusLiveData.value = PlayStatus.Paused(playProgress = START_POSITION)
        } else {
            playerInteractor.pausePlaying()
            playStatusLiveData.value = PlayStatus.Paused(playProgress = playProgress)
        }
        progressTimerJob?.cancel()
    }

    companion object {
        const val START_POSITION = 0
        private const val TIMER_DELAY = 300L
    }
}