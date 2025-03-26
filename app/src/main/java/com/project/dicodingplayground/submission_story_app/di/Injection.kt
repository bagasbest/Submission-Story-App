package com.project.dicodingplayground.submission_story_app.di

import android.content.Context
import com.project.dicodingplayground.submission_story_app.data.UserRepository
import com.project.dicodingplayground.submission_story_app.data.api.ApiConfig
import com.project.dicodingplayground.submission_story_app.data.local.StoryDatabase
import com.project.dicodingplayground.submission_story_app.data.pref.UserPreference
import com.project.dicodingplayground.submission_story_app.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first()}
        val apiService = ApiConfig().getApiService(user.token)
        val database = StoryDatabase.getDatabase(context)
        return UserRepository.getInstance(pref, apiService, database)
    }
}