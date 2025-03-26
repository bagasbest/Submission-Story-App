package com.project.dicodingplayground.practice_modul.animation.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val name: String,
    val password: String,
    val isLogin: Boolean = false
)
