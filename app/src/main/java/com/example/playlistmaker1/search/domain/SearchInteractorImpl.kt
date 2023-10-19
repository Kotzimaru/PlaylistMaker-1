package com.example.playlistmaker1.search.domain

import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.search.domain.api.SearchInteractor
import com.example.playlistmaker1.search.domain.api.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {

    override val historyList = ArrayList<TrackDTO>(getTracksFromHistory())

    override fun getTracksOnQuery(query: String): Flow<FetchResult> {
        return repository.loadTracks(query = query)
    }

    override fun addTrackToHistoryList(track: TrackDTO) {
        when {
            historyList.size < 10 -> {
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

    private fun saveSearchHistory(trackList: ArrayList<TrackDTO>) {
        repository.saveHistory(trackList)
    }

    private fun getTracksFromHistory(): List<TrackDTO> {
        return repository.readHistory()
    }

    companion object {
        private const val FIRST_INDEX_HISTORY_LIST = 0
        private const val LAST_INDEX_HISTORY_LIST = 9
    }
}