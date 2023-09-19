package com.example.playlistmaker1.media.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker1.player.data.TrackDTO

class FavoritesViewModel : ViewModel() {
    val state = MutableLiveData<FavoriteState>(FavoriteState.NOTHING_TO_SHOW)
}

sealed class FavoriteState {
    object NOTHING_TO_SHOW : FavoriteState()
    object LOADING : FavoriteState()
    data class ERROR(val message: String) : FavoriteState()
    data class FAVORITES(val favorites: List<TrackDTO>) : FavoriteState()
}