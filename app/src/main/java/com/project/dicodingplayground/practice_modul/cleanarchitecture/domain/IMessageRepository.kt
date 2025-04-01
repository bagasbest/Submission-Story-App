package com.project.dicodingplayground.practice_modul.cleanarchitecture.domain

interface IMessageRepository {
    fun getWelcomeMessage(name: String): MessageEntity
}