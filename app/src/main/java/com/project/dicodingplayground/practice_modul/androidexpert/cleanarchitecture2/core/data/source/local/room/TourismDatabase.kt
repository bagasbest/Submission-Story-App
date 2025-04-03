package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.local.entity.TourismEntity

@Database(entities = [TourismEntity::class], version = 1, exportSchema = false)
abstract class TourismDatabase: RoomDatabase() {
    abstract fun tourismDao(): TourismDao

    companion object {
        @Volatile
        private var INSTANCE: TourismDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): TourismDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TourismDatabase::class.java,
                    "tourism"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}