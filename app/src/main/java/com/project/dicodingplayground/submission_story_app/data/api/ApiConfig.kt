package com.project.dicodingplayground.submission_story_app.data.api

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.project.dicodingplayground.MyApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    fun getApiService(token: String): ApiService {
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(ChuckerInterceptor(MyApplication.appContext))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    companion object {
        var BASE_URL = "https://story-api.dicoding.dev/v1/"
    }

}