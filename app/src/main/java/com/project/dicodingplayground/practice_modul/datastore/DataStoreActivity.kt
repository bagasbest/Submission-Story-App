package com.project.dicodingplayground.practice_modul.datastore

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.project.dicodingplayground.databinding.ActivityDataStoreBinding

class DataStoreActivity : AppCompatActivity() {

    private var _binding : ActivityDataStoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDataStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            val pref = SettingPreferences.getInstance(application.dataStore)
            val viewModel = ViewModelProvider(this@DataStoreActivity, ViewModelFactory(pref))[DataStoreViewModel::class.java]
            viewModel.getThemeSetting().observe(this@DataStoreActivity) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            }

            switchTheme.setOnCheckedChangeListener { _ : CompoundButton, isChecked : Boolean ->
                viewModel.setThemeSetting(isChecked)
            }
        }
    }
}