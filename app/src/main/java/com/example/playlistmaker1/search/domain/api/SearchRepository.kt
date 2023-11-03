package com.example.playlistmaker1.search.domain.api

import com.example.playlistmaker1.search.data.TrackDTO
import com.example.playlistmaker1.search.domain.FetchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun loadTracks(query: String): Flow<FetchResult>
    fun readHistory(): List<TrackModel>
    fun saveHistory(trackList: ArrayList<TrackModel>)


}