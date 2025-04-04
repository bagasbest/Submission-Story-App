package com.project.tourismcore.data.source.local

import com.project.tourismcore.data.source.local.entity.TourismEntity
import com.project.tourismcore.data.source.local.room.TourismDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource internal constructor(private val tourismDao: TourismDao) {

    fun getAllTourism(): Flow<List<TourismEntity>> = tourismDao.getAllTourism()

    fun getFavoriteTourism(): Flow<List<TourismEntity>> = tourismDao.getFavoriteTourism()

    suspend fun insertTourism(tourismList: List<TourismEntity>) =
        tourismDao.insertTourism(tourismList)

    fun setFavoriteTourism(tourism: TourismEntity, newState: Boolean) {
        tourism.isFavorite = newState
        tourismDao.updateFavoriteTourism(tourism)
    }
}