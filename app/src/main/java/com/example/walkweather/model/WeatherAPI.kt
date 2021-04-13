package com.example.walkweather.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

// Declaring API request functions
interface WeatherAPI {

    @GET
    fun getCurrData(@Url url: String): Single<WeatherData>  // A Single<> is a type of observable

}