package com.example.playlistmaker1.core.di

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.playlistmaker1.core.app.App
import com.example.playlistmaker1.player.domain.api.PlayerRepository
import com.example.playlistmaker1.search.data.network.NetworkClient
import com.example.playlistmaker1.media.data.converter.PlaylistModelConverter
import com.example.playlistmaker1.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker1.search.data.network.SearchApi
import com.example.playlistmaker1.search.data.network.InternetConnectionValidator
import com.example.playlistmaker1.media.data.converter.TrackModelConverter
import com.example.playlistmaker1.player.data.PlayerRepositoryImpl
import com.example.playlistmaker1.search.data.storage.sharedprefs.TracksStorage
import com.example.playlistmaker1.settings.data.storage.sharedprefs.SharedPrefsSettingsStorage
import com.example.playlistmaker1.search.data.storage.sharedprefs.SharedPrefsTracksStorage
import com.example.playlistmaker1.media.data.database.LocalDatabase
import com.example.playlistmaker1.settings.data.storage.sharedprefs.SettingsStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://itunes.apple.com/"

val dataModule = module {

    single<SearchApi> {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .build()

        Retrofit
            .Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(SearchApi::class.java)
    }

    single {
        Room
            .databaseBuilder(androidContext(), LocalDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        androidContext().getSharedPreferences(App.PREFERENCES, AppCompatActivity.MODE_PRIVATE)
    }
    factoryOf(::TrackModelConverter)
    factoryOf(::InternetConnectionValidator)
    factoryOf(::PlaylistModelConverter)
    singleOf(::MediaPlayer)
    singleOf(::RetrofitNetworkClient).bind<NetworkClient>()
    singleOf(::SharedPrefsTracksStorage).bind<TracksStorage>()
    singleOf(::SharedPrefsSettingsStorage).bind<SettingsStorage>()
    singleOf(::PlayerRepositoryImpl).bind<PlayerRepository>()
}