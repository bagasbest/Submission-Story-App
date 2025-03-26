package com.project.dicodingplayground.practice_modul.tdd.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.project.dicodingplayground.practice_modul.tdd.data.NewsRepository
import com.project.dicodingplayground.practice_modul.tdd.data.local.entity.NewsEntity
import kotlinx.coroutines.launch

class NewsDetailViewModel(private val newsRepository: NewsRepository): ViewModel() {

    private val newsData = MutableLiveData<NewsEntity>()

    fun setNewsData(news: NewsEntity) {
        newsData.value = news
    }

    val bookmarkStatus = newsData.switchMap {
        newsRepository.isNewsBookmarked(it.title)
    }

    fun changeBookmark(newsDetail: NewsEntity) {
        viewModelScope.launch {
            if (bookmarkStatus.value as Boolean) {
                newsRepository.deleteNews(newsDetail.title)
            } else {
                newsRepository.saveNews(newsDetail)
            }
        }
    }

}