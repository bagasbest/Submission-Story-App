package com.project.dicodingplayground.submission_fundamental_android.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingPreferences: SettingsPreferences) : ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        return settingPreferences.getThemeSetting().asLiveData()
    }

    fun setThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingPreferences.setThemeSetting(isDarkModeActive)
        }
    }

    fun getDailyReminderSetting(): LiveData<Boolean> {
        return settingPreferences.getDailyReminderSetting().asLiveData()
    }

    fun setDailyReminderSetting(isDailyReminderActive: Boolean) {
        viewModelScope.launch {
            settingPreferences.getDailyReminderSetting(isDailyReminderActive)
        }
    }

}