package com.project.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.tourismcore.domain.usecase.TourismUseCase

class MapsViewModel(tourismUseCase: TourismUseCase): ViewModel() {
    val tourism = tourismUseCase.getAllTourism().asLiveData()
}