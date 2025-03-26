package com.project.dicodingplayground.practice_modul.tdd.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.project.dicodingplayground.practice_modul.tdd.data.NewsRepository
import com.project.dicodingplayground.practice_modul.tdd.data.Result
import com.project.dicodingplayground.practice_modul.tdd.data.local.entity.NewsEntity
import com.project.dicodingplayground.practice_modul.tdd.utils.DataDummy
import com.project.dicodingplayground.practice_modul.tdd.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsViewModel: NewsViewModel
    private val dummyNews = DataDummy.generateDummyNewsEntity()

    @Before
    fun setUp() {
        newsViewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun `when Get HeadlineNews Should Not Null and Return Success`() {
        val observer = Observer<Result<List<NewsEntity>>> {}

        try {
            val expectedNews = MutableLiveData<Result<List<NewsEntity>>>() //Result diambil dari package data yang sudah disiapkan pada starter project
            expectedNews.value = Result.Success(dummyNews)

            `when`(newsRepository.getHeadlineNews()).thenReturn(expectedNews)

            val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()
            Mockito.verify(newsRepository).getHeadlineNews()
            Assert.assertNotNull(actualNews)
            Assert.assertTrue(actualNews is Result.Success)
            Assert.assertEquals(dummyNews.size, (actualNews as Result.Success).data.size)
        } finally {
            newsViewModel.getHeadlineNews().removeObserver(observer)
        }
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val headlineNews = MutableLiveData<Result<List<NewsEntity>>>()
        headlineNews.value = Result.Error("Error")
        `when`(newsRepository.getHeadlineNews()).thenReturn(headlineNews)
        val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()
        Mockito.verify(newsRepository).getHeadlineNews()
        Assert.assertNotNull(actualNews)
        Assert.assertTrue(actualNews is Result.Error)
    }

    @Test
    fun `when Get Bookmark News Should Not Null and Return Success`() {
        val observer = Observer<List<NewsEntity>> {}
        try {
            val expectedBookmarkNews = MutableLiveData<List<NewsEntity>>()
            expectedBookmarkNews.value = dummyNews

            `when`(newsRepository.getBookmarkedNews()).thenReturn(expectedBookmarkNews)

            val actualBookmarkNews = newsViewModel.getBookmarkedNews().getOrAwaitValue()
            Mockito.verify(newsRepository).getBookmarkedNews()
            Assert.assertNotNull(actualBookmarkNews)
            Assert.assertEquals(dummyNews.size, actualBookmarkNews.size)
        } finally {
            newsViewModel.getBookmarkedNews().removeObserver(observer)
        }
    }
}