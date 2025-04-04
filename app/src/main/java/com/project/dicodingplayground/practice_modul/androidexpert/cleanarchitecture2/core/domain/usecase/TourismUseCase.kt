package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.usecase

import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.Resource
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface TourismUseCase {
    fun getAllTourism(): Flow<Resource<List<Tourism>>>
    fun getFavoriteTourism(): Flow<List<Tourism>>
    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}