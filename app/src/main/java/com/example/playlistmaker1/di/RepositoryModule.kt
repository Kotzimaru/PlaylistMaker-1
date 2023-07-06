package com.example.playlistmaker1.di


import android.content.SharedPreferences
import com.example.playlistmaker1.player.data.PlayerRepositoryImpl
import com.example.playlistmaker1.player.domain.api.PlayerRepository
import com.example.playlistmaker1.search.data.SearchRepositoryImpl
import com.example.playlistmaker1.search.domain.api.SearchRepository
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val repositoryModule = module{

    single<SearchRepository>{(sharedPreferences: SharedPreferences) ->
        SearchRepositoryImpl(get(parameters = { parametersOf(sharedPreferences) }), get())
    }
    single<PlayerRepository> {
        PlayerRepositoryImpl()
    }

}