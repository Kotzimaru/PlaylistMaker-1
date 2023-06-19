package com.example.playlistmaker1.search.ui.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker1.player.domain.TrackDTO
import com.example.playlistmaker1.search.domain.SearchInteractor
import com.example.playlistmaker1.search.domain.StateSearch
import com.example.playlistmaker1.search.domain.Uploader

class SearchViewModel(
    sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val searchInteractor: SearchInteractor = SearchInteractor(sharedPreferences)

    private var state = MutableLiveData<Pair<ArrayList<TrackDTO>?, StateSearch>>()

    private var trackList: ArrayList<TrackDTO>? = ArrayList()
    private var historyList: ArrayList<TrackDTO> = ArrayList()

    fun getState(): LiveData<Pair<ArrayList<TrackDTO>?, StateSearch>> = state

    fun uploadTracks(text: String) {
        searchInteractor.uploadTracks(text, object : Uploader {
            override fun getTracks(tracks: ArrayList<TrackDTO>?) {
                if (tracks == null) {
                    // Ошибка соединения
                    state.value = Pair(ArrayList(), StateSearch.NO_CONNECTION)
                } else {
                    if (tracks.isEmpty()) {
                        // Пустой запрос
                        trackList = tracks
                        state.value = Pair(trackList, StateSearch.EMPTY_UPLOAD_TRACKS)
                    } else {
                        // Есть данные
                        trackList = tracks
                        state.value = Pair(trackList, StateSearch.SHOW_UPLOAD_TRACKS)
                    }
                }
            }
        })

    }

    fun getHistory() {

        historyList = searchInteractor.getHistory()

        state.value = if (historyList.isEmpty()) {
            Pair(historyList, StateSearch.EMPTY_HISTORY)
        } else {
            Pair(historyList, StateSearch.SHOW_HISTORY)
        }
    }

    fun setHistory() {
        searchInteractor.setHistory(historyList)
        state.value = if (historyList.isEmpty()) {
            Pair(historyList, StateSearch.EMPTY_HISTORY)
        } else {
            Pair(historyList, StateSearch.SHOW_HISTORY)
        }
    }

    fun clear() {
        historyList = searchInteractor.clear()
    }

    fun removeTrack(track: TrackDTO) {
        historyList = searchInteractor.removeTrack(historyList, track)
        setHistory()
    }
}