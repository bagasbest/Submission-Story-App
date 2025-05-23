package com.project.dicodingplayground.practice_modul.dependencyinjection.data

sealed class Result<out R> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val error: String): Result<Nothing>()
    object Loading: Result<Nothing>()
}