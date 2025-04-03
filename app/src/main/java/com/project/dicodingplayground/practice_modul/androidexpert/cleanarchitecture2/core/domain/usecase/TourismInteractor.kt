package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.usecase

import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.model.Tourism
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.repository.ITourismRepository

class TourismInteractor(private val tourismRepository: ITourismRepository): TourismUseCase {

    override fun getAllTourism() = tourismRepository.getAllTourism()

    override fun getFavoriteTourism() = tourismRepository.getFavoriteTourism()

    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) = tourismRepository.setFavoriteTourism(tourism, state)
}