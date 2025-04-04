package com.project.dicodingplayground.practice_modul.androidexpert.koin

import org.koin.dsl.module

val storageModule = module {
    factory {
        SessionManager(get())
    }

    factory {
        UserRepository(get())
    }
}