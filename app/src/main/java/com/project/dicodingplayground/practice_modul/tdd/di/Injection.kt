package com.project.dicodingplayground.practice_modul.tdd.di

import android.content.Context
import com.project.dicodingplayground.practice_modul.tdd.data.NewsRepository
import com.project.dicodingplayground.practice_modul.tdd.data.local.room.NewsDatabase
import com.project.dicodingplayground.practice_modul.tdd.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}