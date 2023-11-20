package com.example.playlistmaker1.playlist_menu.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker1.media.domain.api.PlaylistsInteractor
import com.example.playlistmaker1.media.ui.models.ScreenState
import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel
import com.example.playlistmaker1.playlist_menu.domain.api.PlaylistDurationCalculator
import com.example.playlistmaker1.search.domain.api.TrackModel
import com.example.playlistmaker1.playlist_menu.ui.models.PlaylistMenuState
import com.example.playlistmaker1.sharing.domain.api.SharingInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistMenuViewModel(
    private val calculator: PlaylistDurationCalculator,
    private val playlistsInteractor: PlaylistsInteractor,
    private val sharingInteractor: SharingInteractor,
) : ViewModel() {
    
    private val _contentFlow: MutableSharedFlow<PlaylistMenuState> = MutableSharedFlow(replay = 1)
    val contentFlow = _contentFlow.asSharedFlow()
    
    private var playlistModel: PlaylistModel? = null
    
    fun fillData(playlistId: Int) {
        viewModelScope.launch {
            playlistsInteractor
                .getPlaylist(playlistId)
                .collect { playlist ->
                    withContext(Dispatchers.Main) {
                        playlistModel = playlist
                        refreshState(playlistModel)
                    }
                }
        }
    }
    
    fun getPlaylist(): PlaylistModel {
        return playlistModel ?: PlaylistModel.emptyPlaylist
    }
    
    fun getPlaylistDuration(): Int {
        return playlistModel?.let { calculator.getTracksDuration(it.trackList) } ?: EMPTY_VALUE
    }
    
    fun getTracksCount(): Int {
        return playlistModel?.tracksCount ?: EMPTY_VALUE
    }
    
    fun deleteTrack(track: TrackModel) {
        viewModelScope.launch {
            playlistModel?.let { playlistsInteractor.deleteTrack(it, track) }
        }
    }
    
    private fun refreshState(playlistModel: PlaylistModel?) {
        if (playlistModel != null) {
            viewModelScope.launch {
                if (playlistModel.trackList.isEmpty()) {
                    _contentFlow.emit(
                        PlaylistMenuState.Content(
                        content = playlistModel,
                        bottomListState = ScreenState.Empty))
                }
                else {
                    _contentFlow.emit(
                        PlaylistMenuState.Content(
                        content = playlistModel,
                        bottomListState = ScreenState.Content(playlistModel.trackList)))
                }
            }
        }
    }
    
    fun shareClicked() {
        viewModelScope.launch {
            if (playlistModel?.trackList.isNullOrEmpty()) {
                _contentFlow.emit(PlaylistMenuState.EmptyShare)
            } else {
                playlistModel?.let { _contentFlow.emit(PlaylistMenuState.Share(it)) }
            }
        }
    }
    
    fun sharePlaylist(message: String) {
        sharingInteractor.share(message)
    }
    
    fun deletePlaylist(playlist: PlaylistModel) {
        viewModelScope.launch {
            playlistsInteractor.deletePlaylist(playlist)
        }
    }
    
    companion object {
        private const val EMPTY_VALUE = 0
    }
}