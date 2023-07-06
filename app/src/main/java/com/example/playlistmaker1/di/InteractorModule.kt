package com.example.playlistmaker1.di


import android.content.SharedPreferences
import com.example.playlistmaker1.player.domain.PlayerInteractorImpl
import com.example.playlistmaker1.player.domain.api.PlayerInteractor
import com.example.playlistmaker1.search.domain.SearchInteractorImpl
import com.example.playlistmaker1.search.domain.api.SearchInteractor
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val interactorModule = module{

    single<SearchInteractor> {(sharedPreferences: SharedPreferences) ->
        SearchInteractorImpl(get(parameters = { parametersOf(sharedPreferences) }))
    }
    single<PlayerInteractor> {
        PlayerInteractorImpl(get())
    }
}