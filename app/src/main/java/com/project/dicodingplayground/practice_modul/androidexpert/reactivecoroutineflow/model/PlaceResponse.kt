package com.project.dicodingplayground.practice_modul.androidexpert.reactivecoroutineflow.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
	@field:SerializedName("features")
	val features: List<PlaceItem>
)
