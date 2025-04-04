package com.project.dicodingplayground

import android.app.Application
import android.content.Context
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.di.useCaseModule
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.di.viewModelModule
import com.project.dicodingplayground.practice_modul.androidexpert.koin.storageModule
import com.project.dicodingplayground.practice_modul.databaserelation.StudentRepository
import com.project.dicodingplayground.practice_modul.databaserelation.database.StudentDatabase
import com.project.tourismcore.di.databaseModule
import com.project.tourismcore.di.networkModule
import com.project.tourismcore.di.repositoryModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class MyApplication : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    storageModule,
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                ),
            )
        }
    }

    private val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { StudentDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { StudentRepository(database.studentDao()) }
}