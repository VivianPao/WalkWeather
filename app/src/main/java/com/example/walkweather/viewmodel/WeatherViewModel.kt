package com.example.walkweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walkweather.model.WeatherData
import com.example.walkweather.model.WeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

// RxJava2 is based on publisher subscriber model!

class WeatherViewModel: ViewModel() {

    val temperature = MutableLiveData<Int>()

    private val weatherService = WeatherService()
    private val disposable = CompositeDisposable()
    // Composite disposable helps with managing resources. Is the stream/ link between the emitter
    // and observer and is meant to be short lived. After we get our needed data, we should stop
    // that stream of information to prevent memory leaks.

    fun refreshWeatherData(){
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        // For this disposable, we add an observer to read the info emitted.
        disposable.add(
            weatherService.getData()
                    .subscribeOn(Schedulers.newThread())    // Read the data on a background thread.
                    // When a subscriber subscribes to a publisher, a stream is created. We choose what thread that stream is on.
                    .observeOn(AndroidSchedulers.mainThread())  // Use the data on the main thread. Brings the data out to be used in main thread.
                    .subscribeWith(object: DisposableSingleObserver<WeatherData>() {    // Create a single observer object with custom functions and use it as subscriber
                        override fun onSuccess(value: WeatherData?) {
                            temperature.value = value?.responseCount
                        }
                        override fun onError(e: Throwable?) {
                            temperature.value = -100
                        }
                    })
        )
        // CompositeDisposable stays around but the things we add to it get disposed once the observer
        // after success/ error of the function/ getData call. That's why fetchData() has to add a new observer to the CompositeDisposable each time.
    }

    fun getTemperature(): Int? {
        return temperature.value // or 23
    }
}