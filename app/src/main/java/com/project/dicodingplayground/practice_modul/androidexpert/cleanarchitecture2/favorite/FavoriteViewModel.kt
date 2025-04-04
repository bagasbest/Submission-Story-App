package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.tourismcore.domain.usecase.TourismUseCase

class FavoriteViewModel(tourismRepository: TourismUseCase): ViewModel() {
    val favoriteTourism = tourismRepository.getFavoriteTourism().asLiveData()
}