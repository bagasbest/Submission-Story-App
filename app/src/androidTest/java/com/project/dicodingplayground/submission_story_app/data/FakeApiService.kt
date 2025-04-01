package com.project.dicodingplayground.submission_story_app.data

import com.project.dicodingplayground.submission_story_app.data.api.ApiService
import com.project.dicodingplayground.submission_story_app.data.api.response.ListStoryItem
import com.project.dicodingplayground.submission_story_app.data.api.response.LoginResponse
import com.project.dicodingplayground.submission_story_app.data.api.response.RegisterResponse
import com.project.dicodingplayground.submission_story_app.data.api.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService: ApiService {
    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): RegisterResponse {
        return RegisterResponse()
    }

    override suspend fun login(
        email: String,
        password: String
    ): LoginResponse {
        return LoginResponse()
    }

    override suspend fun getStories(
        page: Int,
        size: Int,
        location: Int
    ): StoryResponse {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "photo $i",
                "created at $i",
                "name $i",
                "description $i",
                0.0,
                i.toString(),
                0.0
            )
            items.add(story)
        }

        val listStory = items.subList((page - 1) * size, (page - 1) * size + size)
        val storyResponse = StoryResponse(
            listStory,
            null,
            "message",
        )
        return storyResponse
    }

    override suspend fun uploadStory(
        description: RequestBody,
        file: MultipartBody.Part,
        latitude: RequestBody,
        longitude: RequestBody
    ): RegisterResponse {
        return RegisterResponse()
    }

    override suspend fun uploadStoryWithoutLatLng(
        description: RequestBody,
        file: MultipartBody.Part
    ): RegisterResponse {
        return RegisterResponse()
    }

}