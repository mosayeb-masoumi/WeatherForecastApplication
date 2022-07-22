package com.example.weatherapplication.ui.detailActivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.models.Forecastday
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val json = intent.getStringExtra("model")

        val type = object : TypeToken<Forecastday?>() {}.type
        val forecastModel = Gson().fromJson<Forecastday?>(json, type)

        setDetailData(forecastModel)
    }


    @SuppressLint("SetTextI18n")
    private fun setDetailData(model: Forecastday?) {

        model?.date.let {
            txt_detail_date.text = "Forecast for $it"
        }
        model?.day?.let {

            txt_detail_condition.text = it.condition.text

            val url = "https:"+it.condition.icon
            Glide
                .with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(img_deatail)

            txt_max_temp.text = "Max temperature ${it.maxtemp_c} C"
            txt_avg_temp.text = "Average Temperature ${it.avgtemp_c} C"
            txt_min_temp.text = "Min Temperature ${it.mintemp_c} C"
            txt_detail_humidity.text = "Average Humidity ${it.avghumidity}"
            txt_max_wind.text = "Max Wind Speed  ${it.maxwind_kph} KM"
        }
    }
}