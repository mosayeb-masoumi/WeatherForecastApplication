package com.example.weatherapplication.controllers.interfaces

import com.example.weatherapplication.models.Forecastday

interface ForecastItemInteraction {

    fun forecastItemOnclick(model: Forecastday?)
}