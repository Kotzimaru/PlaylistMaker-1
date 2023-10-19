package com.example.playlistmaker1.core.di


import com.example.playlistmaker1.player.data.PlayerRepositoryImpl
import com.example.playlistmaker1.player.domain.api.PlayerRepository
import com.example.playlistmaker1.search.data.SearchRepositoryImpl
import com.example.playlistmaker1.search.domain.api.SearchRepository
import com.example.playlistmaker1.settings.data.SettingsRepositoryImpl
import com.example.playlistmaker1.settings.domain.api.SettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::SearchRepositoryImpl).bind<SearchRepository>()
    singleOf(::SettingsRepositoryImpl).bind<SettingsRepository>()

}