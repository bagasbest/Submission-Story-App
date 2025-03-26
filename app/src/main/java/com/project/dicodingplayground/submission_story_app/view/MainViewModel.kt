package com.project.dicodingplayground.submission_story_app.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.dicodingplayground.submission_story_app.data.UserRepository
import com.project.dicodingplayground.submission_story_app.data.api.payload.LoginPayload
import com.project.dicodingplayground.submission_story_app.data.api.payload.RegisterPayload
import com.project.dicodingplayground.submission_story_app.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun getSession() = repository.getSession()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun register(registerPayload: RegisterPayload) = repository.register(registerPayload)

    fun login(loginPayload: LoginPayload) = repository.login(loginPayload)
}