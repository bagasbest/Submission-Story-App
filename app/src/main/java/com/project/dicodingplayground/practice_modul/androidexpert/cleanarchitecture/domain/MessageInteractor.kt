package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.domain

class MessageInteractor(private val messageRepository: IMessageRepository): MessageUseCase {
    override fun getMessage(name: String): MessageEntity {
        return messageRepository.getWelcomeMessage(name)
    }
}