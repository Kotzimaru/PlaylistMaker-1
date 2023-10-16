package com.example.playlistmaker1.settings.domain.api

import com.example.playlistmaker1.settings.domain.ThemeSettings

interface SettingsRepository {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)
}