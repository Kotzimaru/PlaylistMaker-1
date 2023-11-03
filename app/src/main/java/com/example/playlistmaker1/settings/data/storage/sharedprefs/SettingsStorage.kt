package com.example.playlistmaker1.settings.data.storage.sharedprefs

import com.example.playlistmaker1.settings.data.storage.models.SettingsDto

interface SettingsStorage {
     fun getSettings(): SettingsDto
     fun saveSettings(settingsDto: SettingsDto)
}