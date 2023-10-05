package com.example.playlistmaker1.main.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker1.di.HISTORY
import com.example.playlistmaker1.di.dataModule
import com.example.playlistmaker1.di.interactorModule
import com.example.playlistmaker1.di.repositoryModule
import com.example.playlistmaker1.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {


    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = applicationContext.getSharedPreferences(
            HISTORY,
            MODE_PRIVATE)

        val darkThemeEnabled = sharedPreferences.getBoolean("isDarkTheme",false)

        startKoin {
            androidContext(this@App)
            modules(dataModule,
                repositoryModule,
                interactorModule,
                viewModelModule)
        }

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled){
                AppCompatDelegate.MODE_NIGHT_YES
            }else{
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}