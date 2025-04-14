package com.project.dicodingplayground.practice_modul.tdd.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.dicodingplayground.practice_modul.tdd.data.local.room.NewsDao
import com.project.dicodingplayground.practice_modul.tdd.data.remote.retrofit.ApiService
import com.project.dicodingplayground.practice_modul.tdd.utils.DataDummy
import com.project.dicodingplayground.practice_modul.tdd.utils.MainDispatcherRule
import com.project.dicodingplayground.practice_modul.tdd.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class NewsRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: ApiService
    private lateinit var newsDao: NewsDao
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        apiService = FakeApiService()
        newsDao = FakeNewsDao()
        newsRepository = NewsRepository(apiService, newsDao)
    }

//    @Test
//    fun `when getHeadlineNews Should Not Null`() = runTest {
//        val expectedNews = DataDummy.generateDummyNewsResponse()
//        val actualNews = newsRepository.getHeadlineNews()
//        actualNews.observeForTesting {
//            Assert.assertNotNull(actualNews)
//            Assert.assertEquals(
//                expectedNews.articles.size,
//                (actualNews.value as Result.Success).data.size
//            )
//        }
//    }

    @Test
    fun `when saveNews Should Exist in getBookmarkedNews`() = runTest {
        val sampleNews = DataDummy.generateDummyNewsEntity()[0]
        newsRepository.saveNews(sampleNews)
        val actualNews = newsRepository.getBookmarkedNews().getOrAwaitValue()
        Assert.assertTrue(actualNews.contains(sampleNews))
        Assert.assertTrue(newsRepository.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }

    @Test
    fun `when deleteNews Should Not Exist in getBookmarkedNews`() = runTest {
        val sampleNews = DataDummy.generateDummyNewsEntity()[0]
        newsRepository.deleteNews(sampleNews.title)
        val actualNews = newsRepository.getBookmarkedNews().getOrAwaitValue()
        Assert.assertFalse(actualNews.contains(sampleNews))
        Assert.assertFalse(newsRepository.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }
}