package com.project.dicodingplayground.practice_modul.animation.di

import android.content.Context
import com.project.dicodingplayground.practice_modul.animation.data.UserRepository
import com.project.dicodingplayground.practice_modul.animation.data.pref.UserPreference
import com.project.dicodingplayground.practice_modul.animation.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}