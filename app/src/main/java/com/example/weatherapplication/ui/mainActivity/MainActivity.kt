package com.example.weatherapplication.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.controllers.adapters.ForecastAdapter
import com.example.weatherapplication.controllers.interfaces.ForecastItemInteraction
import com.example.weatherapplication.helper.Resource
import com.example.weatherapplication.models.Current
import com.example.weatherapplication.models.Forecastday
import com.example.weatherapplication.ui.detailActivity.DetailActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ForecastItemInteraction{


    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var adapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_search.setOnClickListener {

            val city  =edt_city.text.toString().trim()
            if(city.isEmpty()){
                Toast.makeText(this, "Enter City Name!!!" , Toast.LENGTH_SHORT).show()
            }else{
                getForecast(city)
            }

        }


    }

    private fun getForecast(city: String) {

        mainViewModel.getForecast(city)

        // wait for observing
        val getForecastLiveData = mainViewModel.getWeatherLiveData
        getForecastLiveData.observe(this) { it ->

            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val result = it.data

                        it.data?.current.let {
                            setCurrentDayData(it)
                            Log.i("TAG", "getForecast: ")
                        }

                        getForecastLiveData.removeObservers(this)
                        mainViewModel.onCleared()  // to prevent looping
                        it.data?.forecast?.forecastday.let {
                            setFutureForecastList(it)
                        }

                        rl_today_forecast.visibility = View.VISIBLE
                        recyclerview.visibility = View.VISIBLE
                        progressbar.visibility = View.GONE
                        txt_error.visibility = View.GONE
                    }
                    Resource.Status.ERROR -> {
//                        Toast.makeText(this, it.message ,Toast.LENGTH_LONG).show()
                        mainViewModel.onCleared()  // to prevent using cache looping
                        progressbar.visibility = View.GONE
                        rl_today_forecast.visibility = View.GONE
                        recyclerview.visibility = View.GONE
                        txt_error.visibility = View.VISIBLE

                        if(it.message?.contains("400") == true){
                            txt_error.text = "No data found!!!"
                        }else{
                            txt_error.text = it.message
                        }

                    }
                    Resource.Status.LOADING -> {
                        progressbar.visibility = View.VISIBLE
                        txt_error.visibility = View.GONE
                        rl_today_forecast.visibility = View.GONE
                        recyclerview.visibility = View.GONE
                    }
                }
            }

        }

    }



    private fun setCurrentDayData(it: Current?) {

        it?.last_updated.let { txt_last_update.text = "Last Update  $it" }
        it?.temp_c.let { txt_tempreture.text = "Tempreture  $it C" }
        it?.wind_kph.let { txt_wind_Speed.text = "Wind Speed  $it  Km" }
        it?.humidity.let { txt_humidity.text = "Humidity $it" }
        it?.condition?.text.let { txt_condition.text = "Condition  $it" }


        val url = "https:"+it?.condition?.icon
        Glide
            .with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(img_current)

    }


    private fun setFutureForecastList(list: List<Forecastday>?) {

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview.layoutManager = layoutManager
        adapter.setAdapterList(list)
        adapter.setListener(this)
        recyclerview.adapter = adapter
    }

    override fun forecastItemOnclick(model: Forecastday?) {

        val gson = Gson()
        val json = gson.toJson(model)

        val intent = Intent(this@MainActivity , DetailActivity::class.java)
        intent.putExtra("model" , json)
        startActivity(intent)
    }
}