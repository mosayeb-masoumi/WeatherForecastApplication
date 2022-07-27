package com.example.weatherapplication.ui.mainActivity

import com.example.weatherapplication.models.WeatherModel


data class MyWeatherState(
    val isLoading: Boolean = false,
//    val memesList: List<Meme> = emptyList(),
    val data: WeatherModel? = null,
    val error : String = ""
)
