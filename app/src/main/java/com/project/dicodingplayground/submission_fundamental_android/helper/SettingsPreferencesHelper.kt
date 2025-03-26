package com.project.dicodingplayground.submission_fundamental_android.helper

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.switchmaterial.SwitchMaterial
import com.project.dicodingplayground.submission_fundamental_android.ui.settings.SettingsViewModel

object SettingsPreferencesHelper {

    fun applyThemeSettings(
        activity: Activity,
        viewModel: SettingsViewModel,
        switchTheme: SwitchMaterial? = null,
        onThemeChanged: () -> Unit = {}
    ) {
        val lifecycleOwner = activity as? LifecycleOwner
            ?: throw IllegalArgumentException("Activity must implement LifecycleOwner")

        viewModel.getThemeSetting().observe(lifecycleOwner) { isDarkModeActive ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )

            switchTheme?.isChecked = isDarkModeActive
            onThemeChanged()
        }
    }

}