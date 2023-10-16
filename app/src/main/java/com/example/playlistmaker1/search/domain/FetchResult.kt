package com.example.playlistmaker1.search.domain

import com.example.playlistmaker1.player.data.TrackDTO

sealed class FetchResult(val data: List<TrackDTO>? = null, val error: NetworkError? = null) {
    class Success(data: List<TrackDTO>): FetchResult(data = data)
    class Error(error: NetworkError): FetchResult(error = error)
}
