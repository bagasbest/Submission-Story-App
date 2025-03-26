package com.project.dicodingplayground.practice_modul.gallerycamera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.dicodingplayground.practice_modul.gallerycamera.data.UploadRepository
import com.project.dicodingplayground.practice_modul.gallerycamera.data.di.Injection

class ViewModelFactory(private val repository: UploadRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }

}