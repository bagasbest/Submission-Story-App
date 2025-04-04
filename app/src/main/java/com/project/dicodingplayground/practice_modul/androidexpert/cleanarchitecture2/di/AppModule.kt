package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.di

import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.detail.DetailTourismViewModel
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.favorite.FavoriteViewModel
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.home.HomeViewModel
import com.project.tourismcore.domain.usecase.TourismInteractor
import com.project.tourismcore.domain.usecase.TourismUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<TourismUseCase> { TourismInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailTourismViewModel(get()) }
}