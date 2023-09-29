package com.example.playlistmaker1.main.app

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker1.di.dataModule
import com.example.playlistmaker1.di.interactorModule
import com.example.playlistmaker1.di.repositoryModule
import com.example.playlistmaker1.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {


        var darkTheme = false


    fun isDarkMode(): Boolean {
        val darkModeFlag = applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onCreate() {
        super.onCreate()
        val sharedPrefs = getSharedPreferences(SAVE_THEME, MODE_PRIVATE)
        darkTheme = sharedPrefs.getBoolean(BOOL_KEY, isDarkMode())
        switchTheme(darkTheme)
        startKoin{
            androidContext(this@App)
            modules(dataModule,
                repositoryModule,
                interactorModule,
                viewModelModule)
        }
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        val sharedPrefs = getSharedPreferences(SAVE_THEME, MODE_PRIVATE)
        sharedPrefs.edit().putBoolean(BOOL_KEY, darkThemeEnabled).apply()
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    companion object {
        const val SAVE_THEME = "save_theme_file"
        const val BOOL_KEY = "key_for_bool"
    }

    }