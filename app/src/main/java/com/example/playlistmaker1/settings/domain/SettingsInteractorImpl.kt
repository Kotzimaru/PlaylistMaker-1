package com.example.playlistmaker1.settings.domain

import com.example.playlistmaker1.settings.domain.api.SettingsInteractor
import com.example.playlistmaker1.settings.domain.api.SettingsRepository

class SettingsInteractorImpl(private val settingsRepository: SettingsRepository):
    SettingsInteractor {
    override fun saveDarkThemeValue(value: Boolean) {
        settingsRepository.saveDarkThemeValue(value)
    }
}