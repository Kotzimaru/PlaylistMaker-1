package com.example.playlistmaker1.search.domain.api

import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.search.domain.Uploader

interface SearchInteractor {

    fun getHistory(): ArrayList<TrackDTO>

    fun setHistory(track: ArrayList<TrackDTO>)

    fun removeTrack(trackList: ArrayList<TrackDTO>, track: TrackDTO): ArrayList<TrackDTO>

    fun uploadTracks(text: String,uploader: Uploader)

    fun clear(): ArrayList<TrackDTO> = ArrayList()

    fun trackToJSON(track: TrackDTO) : String?


}