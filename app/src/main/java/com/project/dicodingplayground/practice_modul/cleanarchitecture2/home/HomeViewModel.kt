package com.project.dicodingplayground.practice_modul.cleanarchitecture2.home

import androidx.lifecycle.ViewModel
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.domain.usecase.TourismUseCase

class HomeViewModel(tourismRepository: TourismUseCase) : ViewModel() {
    val tourism = tourismRepository.getAllTourism()
}