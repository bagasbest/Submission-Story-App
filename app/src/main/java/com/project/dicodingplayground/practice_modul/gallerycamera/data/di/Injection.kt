package com.project.dicodingplayground.practice_modul.gallerycamera.data.di

import com.project.dicodingplayground.practice_modul.gallerycamera.data.UploadRepository
import com.project.dicodingplayground.practice_modul.gallerycamera.data.api.ApiConfig
import com.project.dicodingplayground.practice_modul.gallerycamera.data.api.ApiService

object Injection {
    fun provideRepository() : UploadRepository {
        val apiService: ApiService = ApiConfig.getApiService()
        return UploadRepository.getInstance(apiService)
    }
}