package com.example.playlistmaker1.search.domain.api

import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.search.domain.FetchResult
import com.example.playlistmaker1.search.domain.Uploader
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun loadTracks(query: String): Flow<FetchResult>
    fun readHistory(): List<TrackDTO>
    fun saveHistory(trackList: ArrayList<TrackDTO>)


}