package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.data

import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.domain.MessageEntity

class MessageDataSource : IMessageDataSource {
    override fun getMessageFromSource(name: String) = MessageEntity("Hello $name! Welcome to Clean Architecture")
}