package com.example.playlistmaker1.di

import android.content.SharedPreferences
import com.example.playlistmaker1.player.ui.viewmodels.PlayerViewModel
import com.example.playlistmaker1.search.ui.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module


val viewModelModule = module {

    viewModel { (sharedPreferences: SharedPreferences) ->
        SearchViewModel(get(parameters = { parametersOf(sharedPreferences) }))
    }
    viewModel { (text: String?,toast:() -> Unit) ->
        PlayerViewModel(text, get(),toast)
    }
}