package com.project.dicodingplayground.practice_modul.animation.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.dicodingplayground.practice_modul.animation.data.UserRepository
import com.project.dicodingplayground.practice_modul.animation.data.pref.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }

    fun checkSession(): Flow<UserModel> = userRepository.getSession()

    fun setLogin() {
        viewModelScope.launch {
            userRepository.setLogin()
        }
    }


}