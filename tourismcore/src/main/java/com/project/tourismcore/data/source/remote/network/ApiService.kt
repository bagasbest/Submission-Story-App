package com.project.tourismcore.data.source.remote.network

import com.project.tourismcore.data.source.remote.response.ListTourismResponse
import retrofit2.http.GET

interface ApiService {
    @GET("list")
    suspend fun getList(): ListTourismResponse
}