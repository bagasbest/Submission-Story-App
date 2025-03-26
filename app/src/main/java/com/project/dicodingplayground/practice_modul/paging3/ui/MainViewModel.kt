package com.project.dicodingplayground.practice_modul.paging3.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.project.dicodingplayground.practice_modul.paging3.data.QuoteRepository
import com.project.dicodingplayground.practice_modul.paging3.network.QuoteResponseItem

class MainViewModel(quoteRepository: QuoteRepository) : ViewModel() {

    val quote: LiveData<PagingData<QuoteResponseItem>> =
        quoteRepository.getQuote().cachedIn(viewModelScope)

}