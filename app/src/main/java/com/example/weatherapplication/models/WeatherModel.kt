package com.example.weatherapplication.models

data class WeatherModel(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)