package com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.utils

import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.local.entity.TourismEntity
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.remote.response.TourismResponse

object DataMapper {
    fun mapResponsesToEntities(input: List<TourismResponse>): List<TourismEntity> {
        val tourismList = ArrayList<TourismEntity>()
        input.map {
            val tourism = TourismEntity(
                tourismId = it.id,
                description = it.description,
                name = it.name,
                address = it.address,
                latitude = it.latitude,
                longitude = it.longitude,
                like = it.like,
                image = it.image,
                isFavorite = false
            )
            tourismList.add(tourism)
        }
        return tourismList
    }
}