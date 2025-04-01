package com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.domain.usecase

import androidx.lifecycle.LiveData
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.Resource
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.domain.model.Tourism

interface TourismUseCase {
    fun getAllTourism(): LiveData<Resource<List<Tourism>>>
    fun getFavoriteTourism(): LiveData<List<Tourism>>
    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}