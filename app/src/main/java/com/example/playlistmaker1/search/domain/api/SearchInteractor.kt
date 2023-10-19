package com.example.playlistmaker1.search.domain.api

import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.search.domain.FetchResult
import com.example.playlistmaker1.search.domain.Uploader
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {

    val historyList: ArrayList<TrackDTO>
    fun getTracksOnQuery(query: String): Flow<FetchResult>
    fun addTrackToHistoryList(track: TrackDTO)
    fun historyListCleared()


}