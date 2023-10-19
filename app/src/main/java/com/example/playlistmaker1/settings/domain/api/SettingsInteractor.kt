package com.example.playlistmaker1.settings.domain.api

import com.example.playlistmaker1.settings.domain.ThemeSettings

interface SettingsInteractor {

    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)

}