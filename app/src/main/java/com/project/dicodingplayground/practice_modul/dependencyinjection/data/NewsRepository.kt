package com.project.dicodingplayground.practice_modul.dependencyinjection.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.project.dicodingplayground.practice_modul.dependencyinjection.data.local.entity.NewsEntity
import com.project.dicodingplayground.practice_modul.dependencyinjection.data.local.room.NewsDao
import com.project.dicodingplayground.practice_modul.dependencyinjection.data.remote.retrofit.ApiService
import com.project.dicodingplayground.practice_modul.dependencyinjection.utils.ApiKey
import com.project.dicodingplayground.practice_modul.dependencyinjection.utils.AppExecutors

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao
){

    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews(ApiKey.getNewsApiKey())
            val articles = response.articles
            val newsList = articles?.map { article ->
                val isBookmarked = newsDao.isNewsBookmarked(article?.title.toString())
                NewsEntity(
                    article?.title.toString(),
                    article?.publishedAt.toString(),
                    article?.urlToImage,
                    article?.url,
                    isBookmarked
                )
            }
            newsDao.deleteAll()
            newsDao.insertNews(newsList ?: emptyList())
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }

        val localData: LiveData<Result<List<NewsEntity>>> = newsDao.getNews().map {
            Log.d("LocalData", "Upcoming events count: ${it.size}")
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }
    suspend fun setNewsBookmark(news: NewsEntity, bookmarkState: Boolean) {
        //hapus penggunaan appExecutor
        news.isBookmarked = bookmarkState
        newsDao.updateNews(news)
    }

    companion object {
        @Volatile
        private var INSTANCE: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: NewsRepository(apiService, newsDao)
        }.also {
            INSTANCE = it
        }
    }

}