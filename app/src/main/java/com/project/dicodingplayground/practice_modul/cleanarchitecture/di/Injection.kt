package com.project.dicodingplayground.practice_modul.cleanarchitecture.di

import com.project.dicodingplayground.practice_modul.cleanarchitecture.data.IMessageDataSource
import com.project.dicodingplayground.practice_modul.cleanarchitecture.data.MessageDataSource
import com.project.dicodingplayground.practice_modul.cleanarchitecture.data.MessageRepository
import com.project.dicodingplayground.practice_modul.cleanarchitecture.domain.MessageInteractor
import com.project.dicodingplayground.practice_modul.cleanarchitecture.domain.MessageUseCase

object Injection {
    fun provideUseCase(): MessageUseCase {
        val messageRepository = provideRepository()
        return MessageInteractor(messageRepository)
    }

    private fun provideRepository(): MessageRepository {
        val messageDataSource = provideDataSource()
        return MessageRepository(messageDataSource)
    }

    private fun provideDataSource(): IMessageDataSource {
        return MessageDataSource()
    }
}