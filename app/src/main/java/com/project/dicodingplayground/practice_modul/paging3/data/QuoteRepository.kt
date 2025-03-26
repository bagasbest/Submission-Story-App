package com.project.dicodingplayground.practice_modul.paging3.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.project.dicodingplayground.practice_modul.paging3.database.QuoteDatabase
import com.project.dicodingplayground.practice_modul.paging3.network.ApiService
import com.project.dicodingplayground.practice_modul.paging3.network.QuoteResponseItem

class QuoteRepository(
    private val quoteDatabase: QuoteDatabase,
    private val apiService: ApiService
) {
    fun getQuote(): LiveData<PagingData<QuoteResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = QuoteRemoteMediator(quoteDatabase, apiService),
            pagingSourceFactory = {
                quoteDatabase.quoteDao().getAllQuote()
            }
        ).liveData
    }
}