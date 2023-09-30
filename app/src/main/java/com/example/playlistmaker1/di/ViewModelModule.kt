package com.example.playlistmaker1.di

import android.os.Handler
import android.os.Looper
import com.example.playlistmaker1.media.ui.viewmodels.FavoritesViewModel
import com.example.playlistmaker1.media.ui.viewmodels.PlaylistsViewModel
import com.example.playlistmaker1.player.ui.viewmodels.PlayerViewModel
import com.example.playlistmaker1.search.ui.viewmodels.SearchViewModel
import com.example.playlistmaker1.settings.ui.viewmodels.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel {
        SearchViewModel(get(), get())
    }
    viewModel { (text: String?) ->
        PlayerViewModel(text, get(), get())
    }

    viewModel {
        FavoritesViewModel()
    }
    viewModel {
        PlaylistsViewModel()
    }
    viewModel {
        SettingsViewModel(get())
    }

    single {
        Handler(Looper.myLooper()!!)
    }

}