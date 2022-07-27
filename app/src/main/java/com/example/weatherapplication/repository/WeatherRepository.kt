package com.example.weatherapplication.repository

import com.example.weatherapplication.helper.Constants
import com.example.weatherapplication.helper.Resource
import com.example.weatherapplication.models.WeatherModel
import com.example.weatherapplication.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService) {


    operator  fun invoke(city: String): Flow<Resource<WeatherModel>> = flow {

        try {

            emit(Resource.Loading<WeatherModel>())
            val weatherModel = apiService.getForecast(Constants.API_KEY , city , 7)
            emit(Resource.Success<WeatherModel>(weatherModel))

        }catch (e: HttpException){
            emit(Resource.Error<WeatherModel>(e.localizedMessage ?: "an unexpected error occured"))
        }catch (e: IOException){
            emit(Resource.Error<WeatherModel>(e.localizedMessage ?: "check your internet connection", null))
        }
    }

}