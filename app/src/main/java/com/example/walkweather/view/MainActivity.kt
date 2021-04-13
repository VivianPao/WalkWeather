package com.example.walkweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.walkweather.R
import com.example.walkweather.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // When making VM for fragment, owner = this. VM for activity: owner = this
        val viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel.refreshWeatherData()

        val currTemp = viewModel.getTemperature()

        val tempText: TextView = findViewById(R.id.currTemp)
        tempText.text = formatDeg(currTemp)
    }
}

fun formatDeg(temp: Int?): String {
    var text = "Null value"
    temp?.let{ text = temp.toString()+ "ºC" }   // If not null
    return text
}
