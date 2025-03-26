package com.project.dicodingplayground.submission_story_app.view.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.project.dicodingplayground.submission_story_app.data.UserRepository
import com.project.dicodingplayground.submission_story_app.data.api.response.ListStoryItem

class StoryViewModel(private val repository: UserRepository): ViewModel() {

    val story: LiveData<PagingData<ListStoryItem>> =
        repository.getStories().cachedIn(viewModelScope)

    fun getAllStory() = repository.getAllStoryForMaps()

    fun uploadImage(payload: StoryAddPayload) = repository.uploadImage(payload)

}