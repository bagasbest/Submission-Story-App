package com.project.dicodingplayground.practice_modul.tdd.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.project.dicodingplayground.practice_modul.dependencyinjection.utils.ApiKey
import com.project.dicodingplayground.practice_modul.tdd.data.local.entity.NewsEntity
import com.project.dicodingplayground.practice_modul.tdd.data.local.room.NewsDao
import com.project.dicodingplayground.practice_modul.tdd.data.remote.retrofit.ApiService
import com.project.dicodingplayground.practice_modul.tdd.utils.EspressoIdlingResource.wrapEspressoIdlingResource

class NewsRepository(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {

    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val response = apiService.getNews(ApiKey.getNewsApiKey())
                val articles = response.articles
                val newsList = articles.map { article ->
                    NewsEntity(
                        article.title,
                        article.publishedAt,
                        article.urlToImage,
                        article.url
                    )
                }
                emit(Result.Success(newsList))
            } catch (e: Exception) {
                Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    suspend fun saveNews(news: NewsEntity) {
        newsDao.saveNews(news)
    }

    suspend fun deleteNews(title: String) {
        newsDao.deleteNews(title)
    }

    fun isNewsBookmarked(title: String): LiveData<Boolean> {
        return newsDao.isBookmarked(title)
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(apiService: ApiService, newsDao: NewsDao): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao)
            }.also { instance = it }
    }
}