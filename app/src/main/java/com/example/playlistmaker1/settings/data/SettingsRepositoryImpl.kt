package com.example.playlistmaker1.settings.data

import com.example.playlistmaker1.settings.domain.ThemeSettings
import com.example.playlistmaker1.settings.domain.api.SettingsRepository
import com.example.playlistmaker1.settings.data.storage.models.SettingsDto
import com.example.playlistmaker1.settings.data.storage.sharedprefs.SettingsStorage

class SettingsRepositoryImpl(private val storage: SettingsStorage) : SettingsRepository {
    override fun getThemeSettings(): ThemeSettings {
        return ThemeSettings(
            darkTheme = storage.getSettings().isDarkTheme
        )
    }
    override fun updateThemeSetting(settings: ThemeSettings) {
        storage.saveSettings(
            SettingsDto(
                isDarkTheme = settings.darkTheme
            )
        )
    }

}