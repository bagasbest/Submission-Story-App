package com.project.dicodingplayground.practice_modul.sqlite.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.project.dicodingplayground.practice_modul.sqlite.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.project.dicodingplayground.practice_modul.sqlite.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException

class NoteHelper(context: Context) {

    private val databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAll() : Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(TABLE_NAME, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(TABLE_NAME, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(TABLE_NAME, "$_ID = '$id'", null)
    }

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context) : NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }
    }

}