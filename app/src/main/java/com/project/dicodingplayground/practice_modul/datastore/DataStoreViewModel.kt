package com.project.dicodingplayground.practice_modul.datastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DataStoreViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun setThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.setThemeSetting(isDarkModeActive)
        }
    }
}