package com.example.playlistmaker1.media.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker1.media.domain.api.MediaInteractor
import com.example.playlistmaker1.media.ui.models.ScreenState
import com.example.playlistmaker1.search.domain.api.TrackModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val interactor: MediaInteractor,
) : ViewModel() {

    init {
        fillData()
    }

    private val _contentState = MutableLiveData<ScreenState>()
    val contentState: LiveData<ScreenState> = _contentState

    private fun fillData() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor
                .getSelectedTracks()
                .collect { trackList ->
                    processResult(trackList)
                }
        }
    }

    private fun processResult(trackList: List<TrackModel>) {

        when {
            trackList.isEmpty() -> {
                _contentState.postValue(ScreenState.Empty)
            }

            else -> {
                _contentState.postValue(ScreenState.Content(trackList))
            }
        }
    }
}