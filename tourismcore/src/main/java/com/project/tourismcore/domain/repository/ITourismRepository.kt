package com.project.tourismcore.domain.repository

import com.project.tourismcore.data.Resource
import com.project.tourismcore.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface ITourismRepository {
    fun getAllTourism(): Flow<Resource<List<Tourism>>>

    fun getFavoriteTourism(): Flow<List<Tourism>>

    fun setFavoriteTourism(tourism: Tourism, newState: Boolean)
}