package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.data

import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.domain.IMessageRepository

class MessageRepository(private val messageDataSource: IMessageDataSource): IMessageRepository {
    override fun getWelcomeMessage(name: String) = messageDataSource.getMessageFromSource(name)
}