package com.project.dicodingplayground.practice_modul.cleanarchitecture2.favorite

import androidx.lifecycle.ViewModel
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.domain.usecase.TourismUseCase

class FavoriteViewModel(tourismRepository: TourismUseCase): ViewModel() {
    val favoriteTourism = tourismRepository.getFavoriteTourism()
}