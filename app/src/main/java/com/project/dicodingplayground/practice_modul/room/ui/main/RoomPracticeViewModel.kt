package com.project.dicodingplayground.practice_modul.room.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.dicodingplayground.practice_modul.room.database.Note
import com.project.dicodingplayground.practice_modul.room.repository.NoteRepository

class RoomPracticeViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()

}