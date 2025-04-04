package com.project.dicodingplayground.practice_modul.androidexpert.reactivecoroutineflow.network

import com.project.dicodingplayground.practice_modul.androidexpert.reactivecoroutineflow.model.PlaceResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("geocoding/{query}.json")
    suspend fun getCountry(
        @Path("query") query: String,
        @Query("key") apiKey: String,
    ): PlaceResponse
}