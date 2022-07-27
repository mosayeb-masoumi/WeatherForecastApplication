package com.example.weatherapplication.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.helper.Resource
import com.example.weatherapplication.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository):ViewModel(){


    private val _response = MutableLiveData<MyWeatherState>()
    val weatherLivedata: LiveData<MyWeatherState> get() = _response




    fun getForecast(city : String) {

        weatherRepository(city).onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    _response.value = MyWeatherState(isLoading = true)
                }
                is Resource.Success -> {
                    _response.value = MyWeatherState(data = result.data)
                }
                is Resource.Error -> {
                    _response.value = MyWeatherState(error = "An unexpected error occured" )
                }

            }

        }.launchIn(viewModelScope)
    }

}