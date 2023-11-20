package com.example.playlistmaker1.core.di

import com.example.playlistmaker1.media.ui.viewmodels.FavoritesViewModel
import com.example.playlistmaker1.media.ui.viewmodels.PlaylistsViewModel
import com.example.playlistmaker1.player.ui.viewmodels.PlayerViewModel
import com.example.playlistmaker1.media.ui.viewmodels.BottomSheetViewModel
import com.example.playlistmaker1.search.ui.viewmodels.SearchViewModel
import com.example.playlistmaker1.playlist_creator.ui.view_model.NewPlaylistViewModel
import com.example.playlistmaker1.settings.ui.viewmodels.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val viewModelModule = module {

    viewModelOf(::PlayerViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::PlaylistsViewModel)
    viewModelOf(::NewPlaylistViewModel)
    viewModelOf(::BottomSheetViewModel)

}