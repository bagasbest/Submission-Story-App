package com.project.dicodingplayground.practice_modul.androidexpert.koin

class UserRepository(private val sessionManager: SessionManager) {
    companion object {
        @Volatile
        private var instance: UserRepository? = null

        @JvmStatic
        fun getInstance(sessionManager: SessionManager): UserRepository {
            return instance ?: synchronized(this) {
                instance ?: UserRepository(sessionManager).also { instance = it }
            }
        }
    }

    fun loginUser(username: String) {
        sessionManager.createSession()
        sessionManager.saveToPreference(SessionManager.KEY_USERNAME, username)
    }

    fun getUser() = sessionManager.getFromPreference(SessionManager.KEY_USERNAME)

    fun isUserLogin() = sessionManager.isLogin

    fun logoutUser() = sessionManager.logout()
}