package com.example.playlistmaker1.search.domain.api

import com.example.playlistmaker1.search.domain.FetchResult
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {

    val historyList: ArrayList<TrackModel>
    fun getTracksOnQuery(query: String): Flow<FetchResult>
    fun addTrackToHistoryList(track: TrackModel)
    fun historyListCleared()


}