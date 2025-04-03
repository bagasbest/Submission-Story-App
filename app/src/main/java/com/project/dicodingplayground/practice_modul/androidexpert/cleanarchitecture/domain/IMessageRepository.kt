package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.domain

interface IMessageRepository {
    fun getWelcomeMessage(name: String): MessageEntity
}