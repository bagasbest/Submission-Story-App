package com.project.dicodingplayground.practice_modul.cleanarchitecture.data

import com.project.dicodingplayground.practice_modul.cleanarchitecture.domain.IMessageRepository

class MessageRepository(private val messageDataSource: IMessageDataSource): IMessageRepository {
    override fun getWelcomeMessage(name: String) = messageDataSource.getMessageFromSource(name)
}