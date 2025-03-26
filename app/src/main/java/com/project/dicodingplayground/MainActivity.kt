package com.project.dicodingplayground

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.dicodingplayground.databinding.ActivityMainBinding
import com.project.dicodingplayground.submission_fundamental_android.helper.SettingsPreferencesHelper
import com.project.dicodingplayground.submission_fundamental_android.ui.settings.SettingsPreferences
import com.project.dicodingplayground.submission_fundamental_android.ui.settings.SettingsViewModel
import com.project.dicodingplayground.submission_fundamental_android.ui.settings.SettingsViewModelFactory
import com.project.dicodingplayground.submission_fundamental_android.ui.settings.dataStore

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = SettingsPreferences.getInstance(this.dataStore)
        val settingsViewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(preferences)
        )[SettingsViewModel::class.java]
        SettingsPreferencesHelper.applyThemeSettings(this@MainActivity, settingsViewModel, null) {
            binding.apply {
                startActivity(Intent(this@MainActivity, com.project.dicodingplayground.submission_story_app.view.MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}