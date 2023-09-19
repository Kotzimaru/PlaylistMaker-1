package com.example.playlistmaker1.search.domain

import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.search.domain.api.SearchInteractor
import com.example.playlistmaker1.search.domain.api.SearchRepository

class SearchInteractorImpl(
    private val searchRepository: SearchRepository,
) : SearchInteractor {


    override fun getHistory(): ArrayList<TrackDTO> {
        return searchRepository.getHistory()
    }

    override fun setHistory(track: ArrayList<TrackDTO>) {
        searchRepository.setHistory(track)
    }

    override fun uploadTracks(text: String, uploader: Uploader) {
        searchRepository.searchTrack(text, uploader)
    }

    override fun clear(): ArrayList<TrackDTO> = ArrayList()

    override fun removeTrack(trackList: ArrayList<TrackDTO>, track: TrackDTO): ArrayList<TrackDTO> {
        return searchRepository.removeTrack(trackList, track)
    }

    override fun trackToJSON(track: TrackDTO): String? = searchRepository.trackToJSON(track)

}