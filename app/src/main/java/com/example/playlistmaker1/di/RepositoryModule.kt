package com.example.playlistmaker1.di


import com.example.playlistmaker1.player.data.PlayerRepositoryImpl
import com.example.playlistmaker1.player.domain.api.PlayerRepository
import com.example.playlistmaker1.search.data.SearchRepositoryImpl
import com.example.playlistmaker1.search.domain.api.SearchRepository
import com.example.playlistmaker1.settings.data.SettingsRepositoryImpl
import com.example.playlistmaker1.settings.domain.api.SettingsRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<SearchRepository> {
        SearchRepositoryImpl(get(), get(),get())
    }
    factory<PlayerRepository> {
        PlayerRepositoryImpl(get())
    }
    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

}