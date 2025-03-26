package com.project.dicodingplayground.practice_modul.dependencyinjection.data.remote.retrofit

import com.project.dicodingplayground.practice_modul.dependencyinjection.data.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=us&category=science")
    suspend fun getNews(@Query("apiKey") apiKey: String): NewsResponse //hapus Call<>
}