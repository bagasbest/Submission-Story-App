package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote.network.ApiResponse
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote.network.ApiService
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote.response.TourismResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource(private val apiService: ApiService) {
    @SuppressLint("CheckResult")
    fun getAllTourism(): Flow<ApiResponse<List<TourismResponse>>> {
        // get data from remote api
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.places
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}