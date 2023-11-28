package com.example.playlistmaker1.player.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker1.media.domain.api.MediaInteractor
import com.example.playlistmaker1.player.domain.PlayerState
import com.example.playlistmaker1.player.domain.api.PlayerInteractor
import com.example.playlistmaker1.player.ui.PlayStatus
import com.example.playlistmaker1.search.domain.api.TrackModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val mediaInteractor: MediaInteractor,
) : ViewModel() {

    private val playStatusLiveData = MutableLiveData<PlayStatus>()
    private val isFavoriteLiveData = MutableLiveData<Boolean>()
    private val playProgress get() = playerInteractor.getPlayerPosition()
    private val playerState get() = playerInteractor.getPlayerState()

    private var progressTimerJob: Job? = null
    private var preparePlayerJob: Job? = null
    private var isFavorite: Boolean = false

    override fun onCleared() {
        super.onCleared()
        progressTimerJob?.cancel()
        preparePlayerJob?.cancel()
        playerInteractor.stopPlaying()
    }

    fun observePlayStatus(): LiveData<PlayStatus> = playStatusLiveData
    fun observeFavoriteTrack(): LiveData<Boolean> = isFavoriteLiveData

    fun isFavorite(id: String) {
        viewModelScope.launch() {
            withContext(Dispatchers.IO){
                mediaInteractor.isFavorite(id).collect {
                    isFavorite = it
                    isFavoriteLiveData.postValue(isFavorite)
                }
            }
        }
    }

    fun onViewPaused() {
        pausePlaying()
    }

    fun toggleFavorite(track: TrackModel) {
        isFavorite = !isFavorite
        isFavoriteLiveData.value = isFavorite
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavorite) {
                mediaInteractor.likeTrack(track = track)
            }
            else {
                mediaInteractor.unLikeTrack(track = track)
            }
        }
    }

    fun playButtonClicked(url: String) {
        when (playerState) {
            PlayerState.PLAYING -> pausePlaying()

            PlayerState.NOT_PREPARED -> {
                playStatusLiveData.value = PlayStatus.Loading()
            }

            PlayerState.READY, PlayerState.PAUSED -> startPlaying()

            PlayerState.NOT_CONNECTED -> preparingPlayer(url)
        }
    }

    fun preparingPlayer(url: String) {

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