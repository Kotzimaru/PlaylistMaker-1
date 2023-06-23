package com.example.playlistmaker1.search.domain

import android.content.SharedPreferences
import com.example.playlistmaker1.creator.Creator
import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.search.domain.api.SearchInteractor

class SearchInteractorImpl(private val sharedPrefs: SharedPreferences): SearchInteractor {

    private val searchRepository = Creator.getSearchRepositoryImpl(sharedPrefs)


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

}