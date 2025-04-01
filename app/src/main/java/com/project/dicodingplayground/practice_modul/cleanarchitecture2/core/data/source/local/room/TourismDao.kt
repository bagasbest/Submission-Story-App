package com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.local.entity.TourismEntity

@Dao
interface TourismDao {

    @Query("SELECT * FROM tourism")
    fun getAllTourism(): LiveData<List<TourismEntity>>

    @Query("SELECT * FROM tourism WHERE isFavorite = 1")
    fun getFavoriteTourism(): LiveData<List<TourismEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTourism(tourism: List<TourismEntity>)

    @Update
    fun updateFavoriteTourism(tourism: TourismEntity)
}