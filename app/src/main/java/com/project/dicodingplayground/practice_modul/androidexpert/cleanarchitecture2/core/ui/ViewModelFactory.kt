package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.ui

import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.di.Injection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.usecase.TourismUseCase
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.detail.DetailTourismViewModel
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.favorite.FavoriteViewModel
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.home.HomeViewModel

class ViewModelFactory private constructor(private val tourismUseCase: TourismUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance
                        ?: ViewModelFactory(
                            Injection.provideUseCase(
                                context
                            )
                        )
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(tourismUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(tourismUseCase) as T
            }
            modelClass.isAssignableFrom(DetailTourismViewModel::class.java) -> {
                DetailTourismViewModel(tourismUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}