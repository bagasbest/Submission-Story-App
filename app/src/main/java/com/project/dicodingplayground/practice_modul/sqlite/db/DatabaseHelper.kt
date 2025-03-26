package com.project.dicodingplayground.practice_modul.sqlite.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.project.dicodingplayground.practice_modul.sqlite.db.DatabaseContract.NoteColumns
import com.project.dicodingplayground.practice_modul.sqlite.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_DATABASE_NOTE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        p1: Int,
        p2: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "dbnoteapp"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_DATABASE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${NoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${NoteColumns.TITLE} TEXT NOT NULL," +
                " ${NoteColumns.DESCRIPTION} TEXT NOT NULL," +
                " ${NoteColumns.DATE} TEXT NOT NULL)"
    }

}