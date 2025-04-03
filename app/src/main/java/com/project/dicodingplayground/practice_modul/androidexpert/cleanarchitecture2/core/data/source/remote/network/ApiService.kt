package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote.network

import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote.response.ListTourismResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface ApiService {
    @GET("list")
    fun getList(): Flowable<ListTourismResponse>
}