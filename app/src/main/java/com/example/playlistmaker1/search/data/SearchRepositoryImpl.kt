package com.example.playlistmaker1.search.data
import com.example.playlistmaker1.media.data.TrackModelConverter
import com.example.playlistmaker1.search.data.network.NetworkClient
import com.example.playlistmaker1.search.data.network.SearchResponse
import com.example.playlistmaker1.search.data.storage.sharedprefs.TracksStorage
import com.example.playlistmaker1.search.domain.FetchResult
import com.example.playlistmaker1.search.domain.NetworkError
import com.example.playlistmaker1.search.domain.api.SearchRepository
import com.example.playlistmaker1.search.domain.api.TrackModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SearchRepositoryImpl(
    private val tracksStorage: TracksStorage,
    private val converter: TrackModelConverter,
    private val networkClient: NetworkClient,
) : SearchRepository {

    override fun loadTracks(query: String): Flow<FetchResult> = flow {

        val response = networkClient.doRequest(query)

        when (response.resultCode) {

            in 100..399 -> {
                val resultList = (response as SearchResponse).results
                if (resultList.isEmpty()) {
                    emit(FetchResult.Error(NetworkError.SEARCH_ERROR))
                } else {
                    val trackList = resultList.filter { it.previewUrl != null }
                    emit(FetchResult.Success(trackList.map { converter.map(it) }))
                }
            }
            in 400..499 -> {
                emit(FetchResult.Error(NetworkError.SEARCH_ERROR))
            }
            else -> {
                emit(FetchResult.Error(NetworkError.CONNECTION_ERROR))
            }
        }
    }
    override fun readHistory(): List<TrackModel> {
        return tracksStorage.readHistory().map { converter.map(it) }
    }
    override fun saveHistory(trackList: ArrayList<TrackModel>) {
        tracksStorage.saveHistory(trackList.map { converter.map(it) })
    }
}