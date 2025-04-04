package com.project.dicodingplayground.practice_modul.androidexpert.reactivecoroutineflow.model

import com.google.gson.annotations.SerializedName

data class PlaceItem(
    @field:SerializedName("place_name")
    val placeName: String
)
