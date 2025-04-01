package com.project.dicodingplayground.practice_modul.cleanarchitecture2.home

import androidx.lifecycle.ViewModel
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.TourismRepository

class HomeViewModel(tourismRepository: TourismRepository) : ViewModel() {

    val tourism = tourismRepository.getAllTourism()
}