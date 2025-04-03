package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.domain

interface MessageUseCase {
    fun getMessage(name: String): MessageEntity
}