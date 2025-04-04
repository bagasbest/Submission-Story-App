package com.project.dicodingplayground.submission_fundamental_android.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.dicodingplayground.submission_fundamental_android.data.local.entity.EventEntity
import com.project.dicodingplayground.submission_fundamental_android.data.local.entity.FavoriteEntity

@Database(entities = [EventEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var instance: EventDatabase? = null
        fun getInstance(context: Context): EventDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java, "Event.db"
                ).build()
            }
    }
}