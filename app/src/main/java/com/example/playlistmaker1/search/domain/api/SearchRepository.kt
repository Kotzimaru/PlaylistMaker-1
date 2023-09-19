package com.example.playlistmaker1.search.domain.api

import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.search.domain.Uploader

interface SearchRepository {

    fun getHistory(): ArrayList<TrackDTO>

    fun setHistory(trackList: ArrayList<TrackDTO>?)

    fun removeTrack(trackList: ArrayList<TrackDTO>, track: TrackDTO): ArrayList<TrackDTO>

    fun uploadTracks(text: String)

    fun clear(): ArrayList<TrackDTO> = ArrayList()

    fun searchTrack(text: String, uploader: Uploader)

    fun trackToJSON(track: TrackDTO) : String?


}