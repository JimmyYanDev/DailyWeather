package com.dailyweather.android.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    val places: List<Place>,
    val query: String,
    val status: String
)

data class Place(
    @SerializedName("formatted_address")
    val address: String,
    val id: String,
    val location: Location,
    val name: String,
    val place_id: String
)

data class Location(
    val lat: Double,
    val lng: Double
)