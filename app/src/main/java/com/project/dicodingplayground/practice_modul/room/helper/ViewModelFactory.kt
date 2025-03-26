package com.project.dicodingplayground.practice_modul.room.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.project.dicodingplayground.practice_modul.room.ui.insert.NoteAddUpdateViewModel
import com.project.dicodingplayground.practice_modul.room.ui.main.RoomPracticeViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RoomPracticeViewModel::class.java)) {
            RoomPracticeViewModel(mApplication) as T
        } else {
            NoteAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}