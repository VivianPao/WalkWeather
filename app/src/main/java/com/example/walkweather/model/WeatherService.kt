package com.example.walkweather.model

import com.example.walkweather.model.WeatherData
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// Instantiates API and combines the calls with our important URLs with Retrofit
class WeatherService {
    // http://api.weatherbit.io/v2.0/current?key=5a906745432c470ba2eb84f056dbffd6&city=Sydney&country=Australia
    private val apiKey: String = "?key=5a906745432c470ba2eb84f056dbffd6"
    private val baseURL: String = "http://api.weatherbit.io/v2.0/current/"
    private val location: String = "&city=Sydney&country=Australia"
    private val weatherApi = provideWeatherApi()   // Instantiate API for this service to work with

    // Using Retrofit and URLs, construct the API and return it
    private fun provideWeatherApi(): WeatherAPI {
        val myRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val myWeatherApi = myRetrofit.create(WeatherAPI::class.java)
        return myWeatherApi
    }

    fun getData(): Single<WeatherData> {
        return weatherApi.getCurrData(apiKey + location)
    }

    // Observe LiveData for changes. Update the temperature display value when we see the update.

}