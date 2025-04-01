package com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.di

import android.content.Context
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.TourismRepository
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.local.LocalDataSource
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.local.room.TourismDatabase
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.remote.RemoteDataSource
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.remote.network.ApiConfig
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.domain.usecase.TourismInteractor
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.domain.usecase.TourismUseCase
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.utils.AppExecutors
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): TourismRepository {
        val database = TourismDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.tourismDao())
        val appExecutors = AppExecutors()

        return TourismRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideUseCase(context: Context): TourismUseCase {
        val repository = provideRepository(context)
        return TourismInteractor(repository)
    }
}