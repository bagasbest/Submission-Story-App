package com.project.dicodingplayground.practice_modul.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyBackgroundService : Service() {

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service dijalankan...")
        serviceScope.launch {
            for (i in 0..50) {
                delay(1000)
                Log.d(TAG, "Do something $i")
            }
            stopSelf()
            Log.d(TAG, "Service Dihentikan")
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        Log.d(TAG, "onDestroy: Service dihentikan")
    }

    companion object {
        internal val TAG = MyBackgroundService::class.java.simpleName
    }
}