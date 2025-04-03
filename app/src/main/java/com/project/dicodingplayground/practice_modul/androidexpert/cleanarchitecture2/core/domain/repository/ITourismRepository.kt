package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.repository

import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.Resource
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.model.Tourism
import io.reactivex.rxjava3.core.Flowable

interface ITourismRepository {
    fun getAllTourism(): Flowable<Resource<List<Tourism>>>

    fun getFavoriteTourism(): Flowable<List<Tourism>>

    fun setFavoriteTourism(tourism: Tourism, newState: Boolean)
}