package com.project.dicodingplayground.practice_modul.dependencyinjection.di

import android.content.Context
import com.project.dicodingplayground.practice_modul.dependencyinjection.data.NewsRepository
import com.project.dicodingplayground.practice_modul.dependencyinjection.data.local.room.NewsDatabase
import com.project.dicodingplayground.practice_modul.dependencyinjection.data.remote.retrofit.ApiConfig
import com.project.dicodingplayground.practice_modul.dependencyinjection.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context) : NewsRepository {
        val apiService = ApiConfig().getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }
}