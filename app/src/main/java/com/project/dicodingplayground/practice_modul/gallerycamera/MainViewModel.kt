package com.project.dicodingplayground.practice_modul.gallerycamera

import androidx.lifecycle.ViewModel
import com.project.dicodingplayground.practice_modul.gallerycamera.data.UploadRepository
import java.io.File

class MainViewModel(private val repository: UploadRepository) : ViewModel() {
    fun uploadImage(file: File, description: String) = repository.uploadImage(file, description)
}