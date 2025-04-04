package com.project.dicodingplayground.practice_modul.dependencyinjection.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.dicodingplayground.practice_modul.dependencyinjection.data.NewsRepository
import com.project.dicodingplayground.practice_modul.dependencyinjection.data.local.entity.NewsEntity
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

    fun saveNews(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.setNewsBookmark(news, true)
        }
    }

    fun deleteNews(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.setNewsBookmark(news, false)
        }
    }
}