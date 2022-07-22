package com.example.weatherapplication.ui.mainActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.helper.Resource
import com.example.weatherapplication.models.WeatherModel
import com.example.weatherapplication.use_cases.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase):ViewModel(){


    val getWeatherLiveData: MutableLiveData<Resource<WeatherModel>> = MutableLiveData<Resource<WeatherModel>>()

    fun getForecast(city : String) {
        getWeatherLiveData.value = Resource.Loading()
        weatherUseCase(city).onEach { result ->
            when (result.status) {
                Resource.Status.LOADING -> {
                    getWeatherLiveData.value = Resource.Loading()
                }
                Resource.Status.SUCCESS -> {
                    getWeatherLiveData.value = Resource.Success(result.data)
                }
                Resource.Status.ERROR -> {
                    getWeatherLiveData.value = Resource.Error( result.message+"  error occured!!!")
                }
            }

        }.launchIn(viewModelScope)
    }

    public override fun onCleared() {
        getWeatherLiveData.value = null
    }

}