package com.project.dicodingplayground.practice_modul.cleanarchitecture2.detail

import androidx.lifecycle.ViewModel
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.TourismRepository
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.local.entity.TourismEntity

class DetailTourismViewModel(private val tourismRepository: TourismRepository): ViewModel() {
    fun setFavoriteTourism(tourism: TourismEntity, newStatus: Boolean) = tourismRepository.setFavoriteTourism(tourism, newStatus)
}
