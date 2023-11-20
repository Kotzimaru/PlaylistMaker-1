package com.example.playlistmaker1.core.di

import com.example.playlistmaker1.media.domain.api.MediaInteractor
import com.example.playlistmaker1.media.domain.MediaInteractorImpl
import com.example.playlistmaker1.media.domain.api.PlaylistsInteractor
import com.example.playlistmaker1.media.domain.PlaylistInteractorImpl
import com.example.playlistmaker1.player.domain.api.PlayerInteractor
import com.example.playlistmaker1.player.domain.PlayerInteractorImpl
import com.example.playlistmaker1.search.domain.api.SearchInteractor
import com.example.playlistmaker1.search.domain.SearchInteractorImpl
import com.example.playlistmaker1.settings.domain.SettingsInteractorImpl
import com.example.playlistmaker1.settings.domain.api.SettingsInteractor
import com.example.playlistmaker1.playlist_creator.domain.impl.CreatePlaylistUseCaseImpl
import com.example.playlistmaker1.playlist_creator.domain.api.CreatePlaylistUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val interactorModule = module {

    singleOf(::PlayerInteractorImpl).bind<PlayerInteractor>()
    singleOf(::SearchInteractorImpl).bind<SearchInteractor>()
    singleOf(::SettingsInteractorImpl).bind<SettingsInteractor>()
    singleOf(::MediaInteractorImpl).bind<MediaInteractor>()
    singleOf(::PlaylistInteractorImpl).bind<PlaylistsInteractor>()
    singleOf(::CreatePlaylistUseCaseImpl).bind<CreatePlaylistUseCase>()

}