package com.project.dicodingplayground.practice_modul.tdd.data

import com.project.dicodingplayground.practice_modul.tdd.data.remote.response.NewsResponse
import com.project.dicodingplayground.practice_modul.tdd.data.remote.retrofit.ApiService
import com.project.dicodingplayground.practice_modul.tdd.utils.DataDummy

class FakeApiService : ApiService {
    private val dummyResponse = DataDummy.generateDummyNewsResponse()
    override suspend fun getNews(apiKey: String): NewsResponse {
        return dummyResponse
    }
}