package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.data

import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.domain.MessageEntity

interface IMessageDataSource {
    fun getMessageFromSource(name: String): MessageEntity
}