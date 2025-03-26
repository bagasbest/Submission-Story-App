package com.project.dicodingplayground.submission_fundamental_android.di

import android.content.Context
import com.project.dicodingplayground.submission_fundamental_android.data.EventRepository
import com.project.dicodingplayground.submission_fundamental_android.data.local.room.EventDatabase
import com.project.dicodingplayground.submission_fundamental_android.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig().getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        return EventRepository.getInstance(apiService, dao)
    }
}