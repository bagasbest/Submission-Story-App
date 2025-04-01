package com.project.dicodingplayground.submission_story_app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import com.project.dicodingplayground.submission_story_app.data.api.ApiConfig
import com.project.dicodingplayground.submission_story_app.data.api.ApiService
import com.project.dicodingplayground.submission_story_app.data.api.payload.LoginPayload
import com.project.dicodingplayground.submission_story_app.data.api.payload.RegisterPayload
import com.project.dicodingplayground.submission_story_app.data.api.response.ListStoryItem
import com.project.dicodingplayground.submission_story_app.data.api.response.LoginResponse
import com.project.dicodingplayground.submission_story_app.data.api.response.RegisterResponse
import com.project.dicodingplayground.submission_story_app.data.local.StoryDatabase
import com.project.dicodingplayground.submission_story_app.data.pref.UserModel
import com.project.dicodingplayground.submission_story_app.data.pref.UserPreference
import com.project.dicodingplayground.submission_story_app.view.story.StoryAddPayload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
    private val database: StoryDatabase
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun register(registerPayload: RegisterPayload): LiveData<Result<RegisterResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.register(
                    registerPayload.name,
                    registerPayload.email,
                    registerPayload.password,
                )
                emit(Result.Success(response))
            } catch (e: HttpException) {
                //get error message
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
                val errorMessage = errorBody.message
                e.printStackTrace()
                emit(Result.Error(errorMessage.toString()))
            }
        }

    fun login(loginPayload: LoginPayload): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(
                loginPayload.email,
                loginPayload.password,
            )
            emit(Result.Success(response))
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            val errorMessage = errorBody.message
            e.printStackTrace()
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        val token = runBlocking { getSession().first().token }
        val apiService = ApiConfig().getApiService(token)
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = StoryRemoteMediator(database, apiService),
            pagingSourceFactory = {
                database.storyDao().getAllStory()
            }
        ).liveData
    }

    fun getAllStoryForMaps(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val result: LiveData<Result<List<ListStoryItem>>> = database.storyDao().getAllStoryWithPaging().map { stories ->
                Result.Success(stories)
            }
            emitSource(result)
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            val errorMessage = errorBody.message
            e.printStackTrace()
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun uploadImage(payload: StoryAddPayload) = liveData {
        emit(Result.Loading)
        val descriptionRequestBody = payload.description?.toRequestBody("text/plain".toMediaType())
        val latitudeRequestBody = payload.lat?.toString()?.toRequestBody("text/plain".toMediaType())
        val longitudeRequestBody = payload.lon?.toString()?.toRequestBody("text/plain".toMediaType())
        val requestImageFile = payload.photo?.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            payload.photo?.name,
            requestImageFile!!
        )
        try {
            val token = getSession().first().token
            val successResponse = if (latitudeRequestBody == null && longitudeRequestBody == null) {
                ApiConfig().getApiService(token).uploadStoryWithoutLatLng(
                    descriptionRequestBody!!,
                    multipartBody,
                )
            } else {
                ApiConfig().getApiService(token).uploadStory(
                    descriptionRequestBody!!,
                    multipartBody,
                    latitudeRequestBody!!,
                    longitudeRequestBody!!,
                )
            }
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(Result.Success(errorResponse))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userPreference: UserPreference, apiService: ApiService, database: StoryDatabase): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService, database)
            }.also { instance = it }
    }
}