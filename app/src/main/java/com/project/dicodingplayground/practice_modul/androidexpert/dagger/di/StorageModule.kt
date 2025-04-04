package com.project.dicodingplayground.practice_modul.androidexpert.dagger.di

import android.content.Context
import com.project.dicodingplayground.practice_modul.androidexpert.dagger.SessionManager
import dagger.Module
import dagger.Provides

@Module
class StorageModule {
    @Provides
    fun provideSessionManager(context: Context): SessionManager = SessionManager(context)
}