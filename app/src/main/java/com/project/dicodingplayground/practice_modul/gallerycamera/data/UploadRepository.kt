package com.project.dicodingplayground.practice_modul.gallerycamera.data

import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.project.dicodingplayground.practice_modul.gallerycamera.data.api.ApiConfig
import com.project.dicodingplayground.practice_modul.gallerycamera.data.api.ApiService
import com.project.dicodingplayground.practice_modul.gallerycamera.data.api.FileUploadResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UploadRepository private constructor(
) {
    fun uploadImage(imageFile: File, description: String) = liveData {
        emit(ResultState.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = ApiConfig.getApiService().uploadImage(multipartBody, requestBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
            emit(ResultState.Success(errorResponse))
        }
    }

    companion object {
        @Volatile
        private var instance: UploadRepository? = null
        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: UploadRepository()
            }.also { instance = it }
    }
}