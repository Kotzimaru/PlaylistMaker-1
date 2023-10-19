package com.example.playlistmaker1.core.di

import TracksStorage
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker1.core.app.App
import com.example.playlistmaker1.player.domain.api.PlayerRepository
import com.example.playlistmaker1.search.data.network.NetworkClient
import com.example.playlistmaker1.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker1.search.data.network.SearchApi
import com.example.playlistmaker1.search.data.network.SearchSerializator
import com.example.playlistmaker1.search.data.network.InternetConnectionValidator
import com.example.playlistmaker1.search.domain.api.Serializator
import com.example.playlistmaker1.search.data.TrackModelConverter
import com.example.playlistmaker1.player.data.PlayerRepositoryImpl
import com.practicum.playlistmaker.settings.data.storage.sharedprefs.SharedPrefsSettingsStorage
import com.example.playlistmaker1.search.data.storage.sharedprefs.SharedPrefsTracksStorage
import com.practicum.playlistmaker.settings.data.storage.sharedprefs.SettingsStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://itunes.apple.com/"

val dataModule = module {

    single<SearchApi> {
        Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SearchApi::class.java)
    }

    factory<Serializator> {
        SearchSerializator()
    }

    single {
        androidContext().getSharedPreferences(App.PREFERENCES, AppCompatActivity.MODE_PRIVATE)
    }

    singleOf(::RetrofitNetworkClient).bind<NetworkClient>()
    singleOf(::MediaPlayer)
    singleOf(::InternetConnectionValidator)
    singleOf(::TrackModelConverter)
    singleOf(::SharedPrefsTracksStorage).bind<TracksStorage>()
    singleOf(::SharedPrefsSettingsStorage).bind<SettingsStorage>()
    singleOf(::PlayerRepositoryImpl).bind<PlayerRepository>()
}