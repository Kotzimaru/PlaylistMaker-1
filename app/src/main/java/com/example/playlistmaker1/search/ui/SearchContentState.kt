package com.example.playlistmaker1.search.ui

import com.example.playlistmaker1.search.domain.NetworkError
import com.example.playlistmaker1.search.domain.api.TrackModel

sealed interface SearchContentState {
    object Loading : SearchContentState

    data class SearchContent(
        val trackList: List<TrackModel>,
    ) : SearchContentState

    data class HistoryContent(
        val historyList: List<TrackModel>,
    ) : SearchContentState

    data class Error(
        val error: NetworkError,
    ) : SearchContentState
}