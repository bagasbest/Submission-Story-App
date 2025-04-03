package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.detail

import androidx.lifecycle.ViewModel
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.model.Tourism
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.usecase.TourismUseCase

class DetailTourismViewModel(private val tourismRepository: TourismUseCase): ViewModel() {
    fun setFavoriteTourism(tourism: Tourism, newStatus: Boolean) = tourismRepository.setFavoriteTourism(tourism, newStatus)
}
