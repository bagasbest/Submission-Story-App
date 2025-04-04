package com.project.dicodingplayground.practice_modul.androidexpert.dagger.di

import android.content.Context
import com.project.dicodingplayground.practice_modul.androidexpert.dagger.HomeActivity
import com.project.dicodingplayground.practice_modul.androidexpert.dagger.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: HomeActivity)
}