package com.project.dicodingplayground.submission_fundamental_android.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.dicodingplayground.submission_fundamental_android.data.local.entity.EventEntity
import com.project.dicodingplayground.submission_fundamental_android.data.local.entity.FavoriteEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM event WHERE finished = 0")
    fun getUpcomingEvent(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE finished = 1")
    fun getFinishedEvent(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE favorites = 1 ")
    fun getFavoriteEvent(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(event: List<EventEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteEvent(event: FavoriteEntity)

    @Update
    suspend fun updateEvent(event: EventEntity)

    @Delete
    suspend fun deleteFavoriteEvent(event: FavoriteEntity)

    @Query("DELETE FROM event WHERE favorites = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM event WHERE name = :name AND finished = 1)")
    suspend fun isEventFinished(name: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE name = :name)")
    suspend fun isEventFavorites(name: String): Boolean
}