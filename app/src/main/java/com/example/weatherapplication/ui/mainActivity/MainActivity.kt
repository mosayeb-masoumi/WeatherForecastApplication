package com.example.weatherapplication.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.controllers.adapters.ForecastAdapter
import com.example.weatherapplication.controllers.interfaces.ForecastItemInteraction
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.example.weatherapplication.models.Forecastday
import com.example.weatherapplication.models.WeatherModel
import com.example.weatherapplication.ui.detailActivity.DetailActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ForecastItemInteraction {


    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var adapter: ForecastAdapter


    private var lastCity = ""
    private var currentCity = ""
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSearch.setOnClickListener {

            currentCity = binding.edtCity.text.toString().trim()
            if (currentCity.isEmpty()) {
                Toast.makeText(this, "Enter City Name!!!", Toast.LENGTH_SHORT).show()
            } else {

                // to prevent repetitive call
                if (currentCity != lastCity)
                    getForecast(currentCity)
            }

        }


    }

    private fun getForecast(city: String) {

        mainViewModel.getForecast(city)

        // wait for observing
        val getForecastLiveData = mainViewModel.weatherLivedata
        getForecastLiveData.observe(this) {

            if (it != null) {
                when {

                    it.isLoading -> {
                        binding.progressbar.visibility = View.VISIBLE
                        binding.txtError.visibility = View.GONE
//                        binding.rlTodayForecast.visibility = View.GONE
                    }

                    it.error != "" -> {

                        binding.progressbar.visibility = View.GONE
//                        binding.rlTodayForecast.visibility = View.GONE

                        binding.txtError.visibility = View.VISIBLE

                        if (it.error.contains("400")) {
                            binding.txtError.text = "No data found!!!"
                        } else {
                            binding.txtError.text = it.error
                        }

                    }

                    it.data != null -> {
                        setCurrentDayData(it.data)  // set current data
                        setFutureForecastList(it.data.forecast.forecastday)  // setList
                        binding.rlTodayForecast.visibility = View.VISIBLE
                        binding.progressbar.visibility = View.GONE
                        binding.txtError.visibility = View.GONE

                        lastCity = currentCity // to prevent repetitive call

                    }

                }
            }

        }

    }


    private fun setCurrentDayData(it: WeatherModel?) {


        it?.current?.let {
            binding.txtLastUpdate.text = "Last Update  ${it.last_updated}"
            binding.txtTempreture.text = "Temperature  ${it.temp_c} C"
            binding.txtWindSpeed.text = "Wind Speed  ${it.wind_kph}  Km"
            binding.txtHumidity.text = "Humidity ${it.humidity}"
            binding.txtCondition.text = "Condition  ${it.condition.text}"
        }


        binding.txtCountry.text =
            "Current Weather of " + it?.location?.country + "/" + it?.location?.name


        val url = "https:" + it?.current?.condition?.icon
        Glide
            .with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imgCurrent)

    }


    private fun setFutureForecastList(list: List<Forecastday>?) {

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.layoutManager = layoutManager

        adapter.setListener(this)
        adapter.submitList(list)
        binding.recyclerview.adapter = adapter

    }

    override fun forecastItemOnclick(model: Forecastday?) {

        val gson = Gson()
        val json = gson.toJson(model)

        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("model", json)
        startActivity(intent)
    }
}