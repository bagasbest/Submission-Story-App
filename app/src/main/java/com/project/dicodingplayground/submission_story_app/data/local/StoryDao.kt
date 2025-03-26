package com.project.dicodingplayground.submission_story_app.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.dicodingplayground.submission_story_app.data.api.response.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertStory(stories: List<ListStoryItem>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, ListStoryItem>

    @Query("SELECT * FROM story")
    fun getAllStoryWithPaging(): LiveData<List<ListStoryItem>>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}