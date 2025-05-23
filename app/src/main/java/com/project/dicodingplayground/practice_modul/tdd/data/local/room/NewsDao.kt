package com.project.dicodingplayground.practice_modul.tdd.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.dicodingplayground.practice_modul.tdd.data.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getBookmarkedNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNews(news: NewsEntity)

    @Query("DELETE FROM news WHERE title = :newsTitle")
    suspend fun deleteNews(newsTitle: String)

    @Query("SELECT EXISTS(SELECT * FROM news WHERE title = :title)")
    fun isBookmarked(title: String): LiveData<Boolean>
}