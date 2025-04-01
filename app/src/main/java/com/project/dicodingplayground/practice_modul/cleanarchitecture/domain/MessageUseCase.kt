package com.project.dicodingplayground.practice_modul.cleanarchitecture.domain

interface MessageUseCase {
    fun getMessage(name: String): MessageEntity
}