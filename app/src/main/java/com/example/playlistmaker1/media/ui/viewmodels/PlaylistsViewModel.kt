package com.example.playlistmaker1.media.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker1.media.domain.api.PlaylistsInteractor
import com.example.playlistmaker1.media.ui.models.ScreenState
import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val interactor: PlaylistsInteractor,
) : ViewModel() {

    private val _contentFlow: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Empty)
    val contentFlow: StateFlow<ScreenState> = _contentFlow

    init {
        fillData()
    }

    private fun fillData() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor
                .getPlaylists()
                .collect { playlists ->
                    processResult(playlists)
                }
        }

    }

    private fun processResult(playlists: List<PlaylistModel>) {
        if (playlists.isEmpty()) {
            _contentFlow.value = (ScreenState.Empty)
        } else {
            _contentFlow. value = (ScreenState.Content(playlists))
        }
    }
}