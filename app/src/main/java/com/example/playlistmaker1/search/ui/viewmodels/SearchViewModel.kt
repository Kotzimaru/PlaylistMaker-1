package com.example.playlistmaker1.search.ui.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker1.creator.Creator
import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.search.domain.Uploader
import com.example.playlistmaker1.search.domain.api.StateSearch

class SearchViewModel(sharedPrefs: SharedPreferences) : ViewModel() {

    private val interactor = Creator.getSearchInteractor(sharedPrefs)

    private var state = MutableLiveData<Pair<ArrayList<TrackDTO>?, StateSearch>>()
    private var trackList: ArrayList<TrackDTO>? = ArrayList()
    private var historyList: ArrayList<TrackDTO> = ArrayList()

    fun getState(): LiveData<Pair<ArrayList<TrackDTO>?, StateSearch>> = state

    fun uploadTracks(text: String) {
        interactor.uploadTracks(text, object : Uploader {
            override fun getTracks(tracks: ArrayList<TrackDTO>?) {
                if (tracks == null) {
                    state.value = Pair(ArrayList(), StateSearch.NO_CONNECTION)
                } else {
                    if (tracks.isEmpty()) {
                        trackList = tracks
                        state.value = Pair(trackList, StateSearch.EMPTY_UPLOAD_TRACKS)
                    } else {
                        trackList = tracks
                        state.value = Pair(trackList, StateSearch.SHOW_UPLOAD_TRACKS)
                    }
                }
            }
        })

    }

    fun getHistory() {

        historyList = interactor.getHistory()

        state.value = if (historyList.isEmpty()) {
            Pair(historyList, StateSearch.EMPTY_HISTORY)
        } else {
            Pair(historyList, StateSearch.SHOW_HISTORY)
        }
    }

    fun setHistory() {
        interactor.setHistory(historyList)
        state.value = if (historyList.isEmpty()) {
            Pair(historyList, StateSearch.EMPTY_HISTORY)
        } else {
            Pair(historyList, StateSearch.SHOW_HISTORY)
        }
    }

    fun clear() {
        historyList = interactor.clear()
    }

    fun removeTrack(track: TrackDTO) {
        historyList = interactor.removeTrack(historyList, track)
        setHistory()
    }
}
