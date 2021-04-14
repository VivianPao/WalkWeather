package com.example.walkweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.walkweather.R
import com.example.walkweather.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // When making VM for fragment, owner = this. VM for activity: owner = this
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.refreshWeatherData()

        val tempText: TextView = findViewById(R.id.currTemp)
        tempText.text = "Waiting for data..."

        observeViewModel()
    }

    // // Observe temperature LiveData for when we finally get the weather data from the API.
    private fun observeViewModel() {
        // Lifecycle owner is this main activity.
        weatherViewModel.temperature.observe(this, Observer { temperature ->    // Create an observer with custom functionality. Temperature as an argument

            // If temperature value is not null, update the textview
            temperature?.let{
                val tempText: TextView = findViewById(R.id.currTemp)
                tempText.text = formatDeg(temperature)
            }
        })
    }

    private fun formatDeg(temp: Int?): String {
        var text = "Null value"
        temp?.let{ text = temp.toString()+ "ºC" }   // If not null
        return text
    }
}
