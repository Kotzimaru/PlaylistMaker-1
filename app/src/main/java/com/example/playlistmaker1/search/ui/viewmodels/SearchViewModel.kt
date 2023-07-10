package com.example.playlistmaker1.search.ui.viewmodels

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.player.domain.PlayerState.Companion.CLICK_DEBOUNCE_DELAY
import com.example.playlistmaker1.player.domain.PlayerState.Companion.SEARCH_DEBOUNCE_DELAY
import com.example.playlistmaker1.search.domain.Uploader
import com.example.playlistmaker1.search.domain.api.SearchInteractor
import com.example.playlistmaker1.search.domain.api.StateSearch

class SearchViewModel(
    private val interactor: SearchInteractor,
    private val handler: Handler
) : ViewModel() {

    private var state = MutableLiveData<Pair<ArrayList<TrackDTO>?, StateSearch>>()
    private var trackList: ArrayList<TrackDTO>? = ArrayList()
    private var historyList: ArrayList<TrackDTO> = ArrayList()

    private var textSearch: String = ""

    val searchRunnable = Runnable {
        state.value = Pair(ArrayList(), StateSearch.SEARCH)
        uploadTracks(textSearch)
    }
    init {
        state.value = getDefaultState()
    }

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

    fun trackToJSON(track: TrackDTO): String? = interactor.trackToJSON(track)

    fun clear() {
        historyList = interactor.clear()
    }
    private fun getDefaultState(): Pair<ArrayList<TrackDTO>?, StateSearch> {
        return Pair(ArrayList(), StateSearch.DEFAULT)
    }

    fun removeTrack(track: TrackDTO) {
        historyList = interactor.removeTrack(historyList, track)
        setHistory()
    }

    fun searchDebounse(text: String) {
        //handler.removeCallbacksAndMessages(Any())
        textSearch = text
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }


    fun clickDebounse(): Boolean {
        var isClick = false
        handler.postDelayed({ isClick = true }, CLICK_DEBOUNCE_DELAY)
        return isClick
    }


}
