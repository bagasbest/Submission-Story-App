package com.project.dicodingplayground.practice_modul.androidexpert.reactivecoroutineflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.dicodingplayground.MyApplication
import com.project.dicodingplayground.R
import com.project.dicodingplayground.practice_modul.androidexpert.reactivecoroutineflow.network.ApiConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel: ViewModel() {

    private val accessToken = MyApplication.appContext.getString(R.string.maptiler_api_key)
    val queryChannel = MutableStateFlow("")

    val searchResult = queryChannel
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            ApiConfig.provideApiService().getCountry(it, accessToken).features
        }
        .asLiveData()
}