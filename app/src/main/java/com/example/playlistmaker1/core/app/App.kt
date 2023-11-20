package com.example.playlistmaker1.core.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker1.core.di.dataModule
import com.example.playlistmaker1.core.di.interactorModule
import com.example.playlistmaker1.core.di.repositoryModule
import com.example.playlistmaker1.core.di.viewModelModule
import com.example.playlistmaker1.settings.domain.api.SettingsInteractor
import com.markodevcic.peko.PermissionRequester
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    private var darkTheme = false

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }

        PermissionRequester.initialize(applicationContext)

        darkTheme = getKoin()
            .get<SettingsInteractor>()
            .getThemeSettings().darkTheme

        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    companion object {
        const val PREFERENCES = "app_preferences"
    }
}