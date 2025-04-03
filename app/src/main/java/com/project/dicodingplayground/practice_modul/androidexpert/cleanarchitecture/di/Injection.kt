package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.di

import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.data.IMessageDataSource
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.data.MessageDataSource
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.data.MessageRepository
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.domain.MessageInteractor
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.domain.MessageUseCase

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