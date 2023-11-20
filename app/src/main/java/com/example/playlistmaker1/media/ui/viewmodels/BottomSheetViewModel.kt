package com.example.playlistmaker1.media.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker1.media.domain.api.PlaylistsInteractor
import com.example.playlistmaker1.media.ui.models.BottomSheetState
import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel
import com.example.playlistmaker1.search.domain.api.TrackModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BottomSheetViewModel(
    private val interactor: PlaylistsInteractor,
) : ViewModel() {

    private val _contentFlow: MutableStateFlow<BottomSheetState> =
        MutableStateFlow(BottomSheetState.Empty)
    val contentFlow: StateFlow<BottomSheetState> = _contentFlow

    init {
        fillData()
    }

    fun onPlaylistClicked(playlist: PlaylistModel, track: TrackModel) {
        if (interactor.isTrackAlreadyExists(playlist, track)) {
            _contentFlow.value = BottomSheetState.AddedAlready(playlist)
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                interactor.addTrackToPlaylist(playlist, track)
                _contentFlow.value = BottomSheetState.AddedNow(playlist)
            }
        }
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
            _contentFlow.value = BottomSheetState.Empty
        } else {
            _contentFlow.value = BottomSheetState.Content(playlists)
        }
    }
}