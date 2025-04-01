package com.project.dicodingplayground.practice_modul.cleanarchitecture2.favorite

import androidx.lifecycle.ViewModel
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.TourismRepository

class FavoriteViewModel(tourismRepository: TourismRepository): ViewModel() {
    val favoriteTourism = tourismRepository.getFavoriteTourism()
}