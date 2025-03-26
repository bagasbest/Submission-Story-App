package com.project.dicodingplayground.practice_modul.service

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.project.dicodingplayground.databinding.ActivityMain6Binding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMain6Binding? = null
    private val binding get() = _binding!!
    private var boundStatus = false
    private lateinit var boundService: MyBoundService

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            val myBinder = service as MyBoundService.MyBinder
            boundService = myBinder.getService
            boundStatus = true
            getNumberFromService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            boundStatus = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getNumberFromService() {
        boundService.numberLiveData.observe(this) {
            binding.tvBoundServiceNumber.text = it.toString()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean? ->
        if (!isGranted!!)
            Toast.makeText(this,
                "Unable to display Foreground service notification due to permission decline",
                Toast.LENGTH_LONG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMain6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED)
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        val serviceIntent = Intent(this, MyBackgroundService::class.java)
        val foregroundServiceIntent = Intent(this, MyForegroundService::class.java)
        val boundServiceIntent = Intent(this, MyBackgroundService::class.java)

        binding.apply {
            btnStartBackgroundService.setOnClickListener {
                startService(serviceIntent)
            }

            btnStopBackgroundService.setOnClickListener {
                stopService(serviceIntent)
            }

            btnStartForegroundService.setOnClickListener {
                if (Build.VERSION.SDK_INT >= 26) {
                    startForegroundService(foregroundServiceIntent)
                } else {
                    startService(foregroundServiceIntent)
                }
            }

            btnStopForegroundService.setOnClickListener {
                stopService(foregroundServiceIntent)
            }

            btnStartBoundService.setOnClickListener {
                bindService(boundServiceIntent, connection, BIND_AUTO_CREATE)
            }

            btnStopBoundService.setOnClickListener {
                unbindService(connection)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (boundStatus) {
            unbindService(connection)
            boundStatus = false
        }
    }
}