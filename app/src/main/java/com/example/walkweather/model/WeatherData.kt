package com.example.walkweather.model

import com.google.gson.annotations.SerializedName


// Data class that gets instantiated when we read from JSON
data class WeatherData(
    @SerializedName("data")
    val data: Map<String,Any>,
    @SerializedName("count")
    val responseCount: Int?
)
