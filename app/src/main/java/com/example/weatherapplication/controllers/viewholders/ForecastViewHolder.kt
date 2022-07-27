package com.example.weatherapplication.controllers.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.controllers.interfaces.ForecastItemInteraction
import com.example.weatherapplication.databinding.RowBinding
import com.example.weatherapplication.models.Forecastday

class ForecastViewHolder(private val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Forecastday?) {
        binding.txtDateItem.text = model?.date
        binding.txtConditionItem.text = model?.day?.condition?.text

        val url = "https:" + model?.day?.condition?.icon
        Glide
            .with(itemView.context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imgItem)
    }

    fun setOnListHolderListener(listener: ForecastItemInteraction?, model: Forecastday?) {
        binding.btnMore.setOnClickListener {
            listener?.forecastItemOnclick(model)
        }
    }
}