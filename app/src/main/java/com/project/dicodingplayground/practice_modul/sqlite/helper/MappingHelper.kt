package com.project.dicodingplayground.practice_modul.sqlite.helper

import android.database.Cursor
import com.project.dicodingplayground.practice_modul.sqlite.Note
import com.project.dicodingplayground.practice_modul.sqlite.db.DatabaseContract

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor) : ArrayList<Note> {
        val notesList = ArrayList<Note>()
        notesCursor.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
                notesList.add(Note(
                    id,
                    title,
                    description,
                    date
                ))
            }
        }
        return notesList
    }

}