package com.example.playlistmaker1.settings.data.storage.sharedprefs

import android.content.SharedPreferences
import com.example.playlistmaker1.settings.data.storage.models.SettingsDto

class SharedPrefsSettingsStorage(private val sharedPreferences: SharedPreferences) :
    SettingsStorage {
    
    override fun getSettings(): SettingsDto {
        return SettingsDto(
            isDarkTheme = sharedPreferences.getBoolean(SWITCH_THEME_KEY, false)
        )
    }
    
    override fun saveSettings(settingsDto: SettingsDto) {
        sharedPreferences
                .edit()
                .putBoolean(SWITCH_THEME_KEY, settingsDto.isDarkTheme)
                .apply()
    }
    
    companion object {
        const val SWITCH_THEME_KEY = "theme_preferences"
    }
}