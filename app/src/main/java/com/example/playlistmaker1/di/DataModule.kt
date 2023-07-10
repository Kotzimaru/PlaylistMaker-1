package com.example.playlistmaker1.di

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import com.example.playlistmaker1.search.data.network.SearchApi
import com.example.playlistmaker1.search.data.network.SearchSerializator
import com.example.playlistmaker1.search.domain.api.Serializator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://itunes.apple.com/"
private const val HISTORY = "SearchActivity"

val dataModule = module {

    single {
        MediaPlayer()
    }

    single<SearchApi> {
        Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SearchApi::class.java)
    }

    factory<Serializator> {
        SearchSerializator()
    }

    factory<SharedPreferences> {
        androidContext().getSharedPreferences(HISTORY, Context.MODE_PRIVATE)
    }
}