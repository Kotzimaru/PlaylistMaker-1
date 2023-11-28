package com.example.playlistmaker1.core.di

import com.example.playlistmaker1.media.ui.viewmodels.FavoritesViewModel
import com.example.playlistmaker1.media.ui.viewmodels.PlaylistsViewModel
import com.example.playlistmaker1.player.ui.viewmodels.PlayerViewModel
import com.example.playlistmaker1.media.ui.viewmodels.BottomSheetViewModel
import com.example.playlistmaker1.search.ui.viewmodels.SearchViewModel
import com.example.playlistmaker1.playlist_creator.ui.view_model.PlaylistCreatorViewModel
import com.example.playlistmaker1.settings.ui.viewmodels.SettingsViewModel
import com.example.playlistmaker1.playlist_menu.ui.view_model.PlaylistMenuViewModel
import com.example.playlistmaker1.playlist_redactor.ui.view_model.PlaylistRedactorViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {

    viewModelOf(::PlayerViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::PlaylistsViewModel)
    viewModelOf(::PlaylistCreatorViewModel)
    viewModelOf(::BottomSheetViewModel)
    viewModelOf(::PlaylistMenuViewModel)
    viewModelOf(::PlaylistRedactorViewModel)

}