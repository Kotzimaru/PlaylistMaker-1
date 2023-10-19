package com.example.playlistmaker1.search.ui

import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.search.domain.NetworkError

sealed interface SearchContentState {
    object Loading : SearchContentState

    data class SearchContent(
        val trackList: List<TrackDTO>,
    ) : SearchContentState

    data class HistoryContent(
        val historyList: List<TrackDTO>,
    ) : SearchContentState

    data class Error(
        val error: NetworkError,
    ) : SearchContentState
}