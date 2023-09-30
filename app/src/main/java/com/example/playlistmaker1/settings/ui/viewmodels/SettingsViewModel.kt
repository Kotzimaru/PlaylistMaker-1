package com.example.playlistmaker1.settings.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.playlistmaker1.settings.domain.api.SettingsInteractor

class SettingsViewModel(private val settingsInteractor: SettingsInteractor): ViewModel() {

    fun saveDarkThemeValue(value:Boolean){
        settingsInteractor.saveDarkThemeValue(value)
    }

}