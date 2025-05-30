package com.project.dicodingplayground.practice_modul.dependencyinjection.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.dicodingplayground.practice_modul.dependencyinjection.data.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY publishedAt DESC")
    fun getNews(): LiveData<List<NewsEntity>>

    @Query("SELECT * FROM news where bookmarked = 1")
    fun getBookmarkedNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Update
    suspend fun updateNews(news: NewsEntity)

    @Query("DELETE FROM news WHERE bookmarked = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM news WHERE title = :title AND bookmarked = 1)")
    suspend fun isNewsBookmarked(title: String): Boolean
}