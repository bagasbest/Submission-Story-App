package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListTourismResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("places")
	val places: List<TourismResponse>
)

data class TourismResponse(
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("longitude")
	val longitude: Double,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("like")
	val like: Int,

	@field:SerializedName("image")
	val image: String
)