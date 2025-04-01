package com.project.dicodingplayground.practice_modul.cleanarchitecture.data

import com.project.dicodingplayground.practice_modul.cleanarchitecture.domain.MessageEntity

interface IMessageDataSource {
    fun getMessageFromSource(name: String): MessageEntity
}