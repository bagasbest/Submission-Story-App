package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.tourismcore.domain.usecase.TourismUseCase

class HomeViewModel(tourismRepository: TourismUseCase) : ViewModel() {
    val tourism = tourismRepository.getAllTourism().asLiveData()
}