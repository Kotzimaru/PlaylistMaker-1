package com.example.playlistmaker1.creator

import android.content.SharedPreferences
import com.example.playlistmaker1.player.data.PlayerRepositoryImpl
import com.example.playlistmaker1.player.domain.PlayerInteractorImpl
import com.example.playlistmaker1.player.domain.api.PlayerInteractor
import com.example.playlistmaker1.search.data.SearchRepositoryImpl
import com.example.playlistmaker1.search.domain.SearchInteractorImpl
import com.example.playlistmaker1.search.domain.api.SearchInteractor

object Creator {

    fun getPlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl()
    }

    fun getRepositoryImpl(): PlayerRepositoryImpl {
        return PlayerRepositoryImpl()
    }

    fun getSearchInteractor(sharedPrefs: SharedPreferences): SearchInteractor {
        return  SearchInteractorImpl(sharedPrefs)
    }

    fun getSearchRepositoryImpl(sharedPrefs: SharedPreferences): SearchRepositoryImpl {
        return SearchRepositoryImpl(sharedPrefs)
    }
}