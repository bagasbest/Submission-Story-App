package com.project.dicodingplayground.practice_modul.tdd.data.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.project.dicodingplayground.practice_modul.tdd.utils.DataDummy
import com.project.dicodingplayground.practice_modul.tdd.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NewsDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: NewsDatabase
    private lateinit var newsDao: NewsDao
    private val sampleNews = DataDummy.generateDummyNewsEntity()[0]

    @Before
    fun setDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDatabase::class.java
        ).build()
        newsDao = database.newsDao()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun saveNews_Success() = runTest {
        newsDao.saveNews(sampleNews)
        val actualNews = newsDao.getBookmarkedNews().getOrAwaitValue()
        Assert.assertEquals(sampleNews.title, actualNews[0].title)
        Assert.assertTrue(newsDao.isBookmarked(sampleNews.title).getOrAwaitValue())
    }

    @Test
    fun deleteNews_Success() = runTest {
        newsDao.deleteNews(sampleNews.title)
        val actualNews = newsDao.getBookmarkedNews().getOrAwaitValue()
        Assert.assertTrue(actualNews.isEmpty())
        Assert.assertFalse(newsDao.isBookmarked(sampleNews.title).getOrAwaitValue())
    }
}