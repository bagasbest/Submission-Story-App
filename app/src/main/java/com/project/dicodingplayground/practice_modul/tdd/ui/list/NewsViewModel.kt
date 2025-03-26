package com.project.dicodingplayground.practice_modul.tdd.ui.list

import androidx.lifecycle.ViewModel
import com.project.dicodingplayground.practice_modul.tdd.data.NewsRepository

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {

    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

}