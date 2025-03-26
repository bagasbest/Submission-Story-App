package com.project.dicodingplayground.practice_modul.animation.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.dicodingplayground.practice_modul.animation.data.UserRepository
import com.project.dicodingplayground.practice_modul.animation.data.pref.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getSession(): Flow<UserModel> {
        return userRepository.getSession()
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

}