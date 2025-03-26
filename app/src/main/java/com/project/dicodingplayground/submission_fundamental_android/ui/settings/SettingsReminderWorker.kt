package com.project.dicodingplayground.submission_fundamental_android.ui.settings

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.project.dicodingplayground.R
import com.project.dicodingplayground.submission_fundamental_android.data.remote.retrofit.ApiConfig
import com.project.dicodingplayground.submission_fundamental_android.helper.DateFormatter


class SettingsReminderWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return return try {
            val eventResponse = ApiConfig().getApiService().getNewestEvents()

            val event = eventResponse.listEvents.firstOrNull()
            if (event != null) {
                val beginTime = DateFormatter.formatDate(
                    event.beginTime,
                    "yyyy-MM-dd HH:mm:ss",
                    "EEEE, dd MMMM yyyy"
                )
                showNotification(event.name, beginTime.toString())
                Result.success()
            } else {
                showNotification("Get Newest Event Failed", "No event found")
                Result.failure()
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception during network call", e)
            showNotification("Get Newest Event Failed", e.message ?: "Unknown error")
            Result.failure()
        }
    }

    private fun showNotification(title: String, description: String) {
        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

    companion object {
        private val TAG = SettingsReminderWorker::class.java.simpleName
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "dicoding channel"
    }

}