package com.example.playlistmaker1.di


import com.example.playlistmaker1.player.domain.PlayerInteractorImpl
import com.example.playlistmaker1.player.domain.api.PlayerInteractor
import com.example.playlistmaker1.search.domain.SearchInteractorImpl
import com.example.playlistmaker1.search.domain.api.SearchInteractor
import com.example.playlistmaker1.settings.domain.SettingsInteractorImpl
import com.example.playlistmaker1.settings.domain.api.SettingsInteractor
import org.koin.dsl.module

val interactorModule = module {

    factory<SearchInteractor> {
        SearchInteractorImpl(get())
    }
    factory<PlayerInteractor> {
        PlayerInteractorImpl(get())
    }
    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }


}