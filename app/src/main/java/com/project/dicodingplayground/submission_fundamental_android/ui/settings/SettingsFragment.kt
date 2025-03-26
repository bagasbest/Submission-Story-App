package com.project.dicodingplayground.submission_fundamental_android.ui.settings

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.project.dicodingplayground.databinding.FragmentSettingsBinding
import android.Manifest
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import com.google.android.material.snackbar.Snackbar
import com.project.dicodingplayground.submission_fundamental_android.helper.SettingsPreferencesHelper
import java.util.concurrent.TimeUnit

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var workManager: WorkManager
    private lateinit var periodicWorkRequest: PeriodicWorkRequest


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Snackbar.make(binding.itemRoot, "Please enable notification permission in settings.", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        requestNotificationPermission()

        workManager = WorkManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {
            val preferences = SettingsPreferences.getInstance(requireActivity().dataStore)
            val settingsViewModel = ViewModelProvider(
                requireActivity(),
                SettingsViewModelFactory(preferences)
            )[SettingsViewModel::class.java]

            SettingsPreferencesHelper.applyThemeSettings(
                requireActivity(),
                settingsViewModel,
                switchTheme
            )

            settingsViewModel.getDailyReminderSetting()
                .observe(viewLifecycleOwner) { isDailyReminderActive: Boolean ->
                    if (isDailyReminderActive) {
                        startPeriodicTask()
                        switchReminder.isChecked = true
                    } else {
                        cancelPeriodicTask()
                        switchReminder.isChecked = false
                    }
                }

            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                settingsViewModel.setThemeSetting(isChecked)
            }

            switchReminder.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                settingsViewModel.setDailyReminderSetting(isChecked)
            }

        }
    }

    /**
     * Schedules a unique periodic work request that will run once a day.
     * If a work request with the same unique name exists, it will be kept.
     */
    private fun startPeriodicTask() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        periodicWorkRequest =
            PeriodicWorkRequest.Builder(SettingsReminderWorker::class.java, 1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
        workManager.enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(viewLifecycleOwner) { workInfo ->
                val status = workInfo?.state?.name
                Log.d("STATUS", status.toString())
            }
    }

    private fun cancelPeriodicTask() {
        workManager.cancelUniqueWork(UNIQUE_WORK_NAME)
    }

    companion object {
        const val UNIQUE_WORK_NAME = "daily_reminder_work"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}