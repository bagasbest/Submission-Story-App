package com.project.dicodingplayground.submission_fundamental_android.data.remote.retrofit

import com.project.dicodingplayground.submission_fundamental_android.data.remote.response.EventResponse
import retrofit2.http.GET

interface ApiService {
    @GET("events?active=-1&limit=1")
    suspend fun getNewestEvents(): EventResponse

    @GET("events?active=1")
    suspend fun getUpcomingEvents(): EventResponse

    @GET("events?active=0")
    suspend fun getFinishedEvents(): EventResponse
}