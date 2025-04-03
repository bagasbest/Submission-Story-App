package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.usecase.TourismUseCase

class FavoriteViewModel(tourismRepository: TourismUseCase): ViewModel() {
    val favoriteTourism = tourismRepository.getFavoriteTourism().toLiveData()
}