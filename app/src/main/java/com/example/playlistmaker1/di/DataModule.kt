package com.example.playlistmaker1.di

import android.media.MediaPlayer
import com.example.playlistmaker1.search.data.network.SearchApi
import com.example.playlistmaker1.search.data.network.SearchSerializator
import com.example.playlistmaker1.search.domain.api.Serializator
import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val URL = "https://itunes.apple.com/"

val dataModule = module {

    factory {
        MediaPlayer()
    }
    factory {
        Gson()
    }

    factory <SearchApi> {
        Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create()
    }

    factory<Serializator> {
        SearchSerializator()
    }
}