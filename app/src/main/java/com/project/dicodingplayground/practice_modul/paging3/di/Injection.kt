package com.project.dicodingplayground.practice_modul.paging3.di

import android.content.Context
import com.project.dicodingplayground.practice_modul.paging3.data.QuoteRepository
import com.project.dicodingplayground.practice_modul.paging3.database.QuoteDatabase
import com.project.dicodingplayground.practice_modul.paging3.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): QuoteRepository {
        val database = QuoteDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return QuoteRepository(database, apiService)
    }
}