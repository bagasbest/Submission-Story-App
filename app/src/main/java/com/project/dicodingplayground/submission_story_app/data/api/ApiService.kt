package com.project.dicodingplayground.submission_story_app.data.api

import com.project.dicodingplayground.submission_story_app.data.api.response.LoginResponse
import com.project.dicodingplayground.submission_story_app.data.api.response.RegisterResponse
import com.project.dicodingplayground.submission_story_app.data.api.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ) : LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int = 1
    ) : StoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("lat") latitude: RequestBody,
        @Part("lon") longitude: RequestBody,
    ) : RegisterResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStoryWithoutLatLng(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
    ) : RegisterResponse
}
