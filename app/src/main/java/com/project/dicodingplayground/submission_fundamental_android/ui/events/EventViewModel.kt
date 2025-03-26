package com.project.dicodingplayground.submission_fundamental_android.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.dicodingplayground.submission_fundamental_android.data.EventRepository
import com.project.dicodingplayground.submission_fundamental_android.data.local.entity.EventEntity
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventRepository): ViewModel() {
    fun getEvent(optionSelected: String) = repository.getEvent(optionSelected)

    fun getFavoriteEvent() = repository.getFavoriteEvent()

    fun saveEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.setFavoriteEvent(event, true)
        }
    }
    fun deleteEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.setFavoriteEvent(event, false)
        }
    }
}