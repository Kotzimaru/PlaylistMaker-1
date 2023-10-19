package com.example.playlistmaker1.settings.ui.viewmodels

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker1.settings.domain.ThemeSettings
import com.example.playlistmaker1.settings.domain.api.SettingsInteractor

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    private var darkTheme = false
    private val themeSwitcherStateLiveData = MutableLiveData(darkTheme)

    init {
        darkTheme = settingsInteractor.getThemeSettings().darkTheme
        themeSwitcherStateLiveData.value = darkTheme
    }

    fun observeThemeSwitcherState(): LiveData<Boolean> = themeSwitcherStateLiveData

    fun onThemeSwitcherClicked(isChecked: Boolean) {
        themeSwitcherStateLiveData.value = isChecked
        settingsInteractor.updateThemeSetting(ThemeSettings(darkTheme = isChecked))

        themeSwitcher(isChecked)
        Log.d("TEST", "+++ changeTheme = $isChecked +++")
    }

    private fun themeSwitcher(darkThemeEnabled: Boolean) {

        darkTheme = darkThemeEnabled

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}