package com.example.playlistmaker1.settings.domain

import com.example.playlistmaker1.settings.data.SettingsRepositoryImpl
import com.example.playlistmaker1.settings.domain.api.SettingsInteractor
import com.example.playlistmaker1.settings.domain.api.SettingsRepository

class SettingsInteractorImpl(private val repository: SettingsRepository) : SettingsInteractor {
    override fun getThemeSettings(): ThemeSettings {
        return repository.getThemeSettings()
    }

    override fun updateThemeSetting(settings: ThemeSettings) {
        repository.updateThemeSetting(settings)
    }
}