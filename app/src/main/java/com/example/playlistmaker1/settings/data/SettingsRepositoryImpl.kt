package com.example.playlistmaker1.settings.data

import android.content.SharedPreferences
import com.example.playlistmaker1.settings.domain.api.SettingsRepository

class SettingsRepositoryImpl(private val sharedPreferences: SharedPreferences): SettingsRepository {
    override fun saveDarkThemeValue(value: Boolean) {
        sharedPreferences.edit().putBoolean("isDarkTheme",value).apply()
    }
}