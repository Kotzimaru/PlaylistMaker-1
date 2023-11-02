package com.example.playlistmaker1.search.domain

import com.example.playlistmaker1.search.domain.api.SearchInteractor
import com.example.playlistmaker1.search.domain.api.SearchRepository
import com.example.playlistmaker1.search.domain.api.TrackModel
import kotlinx.coroutines.flow.Flow

class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {

    override val historyList = ArrayList<TrackModel>(getTracksFromHistory())

    override fun getTracksOnQuery(query: String): Flow<FetchResult> {
        return repository.loadTracks(query = query)
    }

    override fun addTrackToHistoryList(track: TrackModel) {
        when {
            historyList.size < 10 || historyList.contains(track) -> {
                historyList.remove(track)
                historyList.add(FIRST_INDEX_HISTORY_LIST, track)
            }

            else -> {
                historyList.removeAt(LAST_INDEX_HISTORY_LIST)
                historyList.add(FIRST_INDEX_HISTORY_LIST, track)
            }
        }
        saveSearchHistory(historyList)
    }

    override fun historyListCleared() {
        historyList.clear()
        saveSearchHistory(historyList)
    }

    private fun saveSearchHistory(trackList: ArrayList<TrackModel>) {
        repository.saveHistory(trackList)
    }

    private fun getTracksFromHistory(): List<TrackModel> {
        return repository.readHistory()
    }

    companion object {
        private const val FIRST_INDEX_HISTORY_LIST = 0
        private const val LAST_INDEX_HISTORY_LIST = 9
    }
}