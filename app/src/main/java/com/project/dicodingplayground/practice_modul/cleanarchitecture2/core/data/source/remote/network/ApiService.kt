package com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.remote.network

import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.remote.response.ListTourismResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("list")
    fun getList(): Call<ListTourismResponse>
}