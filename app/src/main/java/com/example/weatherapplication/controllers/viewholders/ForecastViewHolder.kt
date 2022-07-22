package com.example.weatherapplication.controllers.viewholders

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.controllers.interfaces.ForecastItemInteraction
import com.example.weatherapplication.models.Forecastday
import kotlinx.android.synthetic.main.activity_main.*

class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val txtDate: TextView = view.findViewById(R.id.txt_date_item)
    private val txtCondition: TextView = view.findViewById(R.id.txt_condition_item)
    private val img: ImageView = view.findViewById(R.id.img_item)
    private val btnMore: Button = view.findViewById(R.id.btn_more)

    fun bindData(model: Forecastday?) {

        var fff = model
        Log.i("TAG", "bindData: ")

        model?.date.let {
            txtDate.text = it
        }

        model?.day?.condition.let {
            txtCondition.text
            val url = "https:"+it?.icon
            Glide
                .with(itemView.context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(img)

        }


    }

    fun setOnListHolderListener(listener: ForecastItemInteraction?, model: Forecastday?) {

        btnMore.setOnClickListener {
            listener?.forecastItemOnclick(model)
        }
    }
}