package com.example.playlistmaker1.di

import android.os.Handler
import android.os.Looper
import com.example.playlistmaker1.player.ui.viewmodels.PlayerViewModel
import com.example.playlistmaker1.search.ui.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel {
        SearchViewModel(get(), get())
    }
    viewModel { (text: String?) ->
        PlayerViewModel(text, get(), get())
    }

    single {
        Handler(Looper.myLooper()!!)
    }
}