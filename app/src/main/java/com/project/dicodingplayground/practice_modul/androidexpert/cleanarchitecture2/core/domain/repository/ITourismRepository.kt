package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.repository

import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.Resource
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface ITourismRepository {
    fun getAllTourism(): Flow<Resource<List<Tourism>>>

    fun getFavoriteTourism(): Flow<List<Tourism>>

    fun setFavoriteTourism(tourism: Tourism, newState: Boolean)
}