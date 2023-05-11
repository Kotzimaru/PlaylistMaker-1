package com.example.playlistmaker1.data

import com.example.playlistmaker1.data.dto.RequestGetTrack
import com.example.playlistmaker1.data.dto.Response
import com.example.playlistmaker1.domain.api.TracksRepository
import com.example.playlistmaker1.domain.models.Track

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {
    private val cachedTracks = mutableMapOf<String, Track>()

    override fun likeTrack(trackId: String) {
        val response = networkClient.doRequest(RequestGetTrack(trackId = trackId)) as Response

        if (response.resultCode == 1) {
            cachedTracks[trackId]?.let { track: Track ->
                cachedTracks[trackId] = track.copy(isLiked = false)
            }
        }
    }

    override fun unlikeTrack(trackId: String) {
        val response = networkClient.doRequest(RequestGetTrack(trackId = trackId)) as Response

        if (response.resultCode == 1) {
            cachedTracks[trackId]?.let { track: Track ->
                cachedTracks[trackId] = track.copy(isLiked = true)
            }
        }
    }

    override fun getTrackForId(trackId: String): Track {
        val response = networkClient.doRequest(RequestGetTrack(trackId = trackId)) as Track

        cachedTracks[trackId] = response

        return response
    }
}