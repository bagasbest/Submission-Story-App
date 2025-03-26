package com.project.dicodingplayground

import android.app.Application
import android.content.Context
import com.project.dicodingplayground.practice_modul.databaserelation.StudentRepository
import com.project.dicodingplayground.practice_modul.databaserelation.database.StudentDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    private val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { StudentDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { StudentRepository(database.studentDao()) }
}