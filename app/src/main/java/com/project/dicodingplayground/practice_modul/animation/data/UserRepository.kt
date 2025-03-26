package com.project.dicodingplayground.practice_modul.animation.data

import com.project.dicodingplayground.practice_modul.animation.data.pref.UserModel
import com.project.dicodingplayground.practice_modul.animation.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(private val userPreference: UserPreference) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun setLogin() {
        userPreference.setLogin()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}