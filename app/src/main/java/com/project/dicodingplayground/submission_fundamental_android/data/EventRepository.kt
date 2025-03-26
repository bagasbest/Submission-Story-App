package com.project.dicodingplayground.submission_fundamental_android.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.project.dicodingplayground.submission_fundamental_android.data.local.entity.EventEntity
import com.project.dicodingplayground.submission_fundamental_android.data.local.entity.FavoriteEntity
import com.project.dicodingplayground.submission_fundamental_android.data.local.room.EventDao
import com.project.dicodingplayground.submission_fundamental_android.data.remote.retrofit.ApiService

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao
) {

    fun getEvent(optionSelected: String): LiveData<Result<List<EventEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = if (optionSelected == "UPCOMING") {
                apiService.getUpcomingEvents()
            } else {
                apiService.getFinishedEvents()
            }
            val events = response.listEvents
            val finishedFlag = optionSelected != "UPCOMING"
            val eventList = events.map { event ->
                val isFavorite = eventDao.isEventFavorites(event.name.toString())
                EventEntity(
                    event.name,
                    event.summary,
                    event.description,
                    event.imageLogo,
                    event.mediaCover,
                    event.category,
                    event.cityName,
                    event.link,
                    event.beginTime,
                    event.endTime,
                    event.ownerName,
                    event.quota,
                    event.registrants,
                    finishedFlag,
                    isFavorite
                )
            }

            val eventFavoriteList = eventList.filter { it.isFavorites == true }
            eventFavoriteList.forEach { event ->
                val data = FavoriteEntity(event.name)
                eventDao.addFavoriteEvent(data)
            }


            eventDao.deleteAll()
            eventDao.insertEvent(eventList)
        } catch (e : Exception) {
            Log.d("EventRepository", "$optionSelected: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }

        // Log the local data as it is emitted from the database.
        val localData: LiveData<Result<List<EventEntity>>> = if (optionSelected == "UPCOMING") {
            eventDao.getUpcomingEvent().map { events ->
                Log.d("LocalData", "Upcoming events count: ${events.size}")
                Result.Success(events)
            }
        } else {
            eventDao.getFinishedEvent().map { events ->
                Log.d("LocalData", "Finished events count: ${events.size}")
                Result.Success(events)
            }
        }
        emitSource(localData)
    }

    fun getFavoriteEvent(): LiveData<List<EventEntity>> {
        return eventDao.getFavoriteEvent()
    }

    suspend fun setFavoriteEvent(event: EventEntity, isFavoriteEvent: Boolean) {
        event.isFavorites = isFavoriteEvent
        eventDao.updateEvent(event)

        val data = FavoriteEntity(event.name)
        if (isFavoriteEvent) {
            eventDao.addFavoriteEvent(data)
        } else {
            eventDao.deleteFavoriteEvent(data)
        }
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: EventDao
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, newsDao)
            }.also { instance = it }
    }

}