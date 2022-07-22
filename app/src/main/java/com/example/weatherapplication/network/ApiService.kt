package com.example.weatherapplication.network

import com.example.weatherapplication.models.WeatherModel

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/forecast.json")
    suspend fun getForecast(
        @Query(value = "key") key: String?,
        @Query(value = "q") q: String?,
        @Query(value = "days") days: Int?,
    ): WeatherModel

}