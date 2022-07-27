package com.example.weatherapplication.controllers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.weatherapplication.controllers.interfaces.ForecastItemInteraction
import com.example.weatherapplication.controllers.viewholders.ForecastViewHolder
import com.example.weatherapplication.databinding.RowBinding
import com.example.weatherapplication.models.Forecastday
import javax.inject.Inject


class ForecastAdapter @Inject constructor():
        ListAdapter<Forecastday, ForecastViewHolder>(MyDiffUtil()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {

        val model = getItem(position)
        holder.bind(getItem(position)!!)
        holder.setOnListHolderListener(listener, model)
    }



    class MyDiffUtil: DiffUtil.ItemCallback<Forecastday>() {
            override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
                return oldItem.astro == newItem.astro
//                        && oldItem.date == newItem.date
//                        && oldItem.date_epoch == newItem.date_epoch
//                        && oldItem.day == newItem.day
//                        && oldItem.hour == newItem.hour
            }

        }



    private var listener: ForecastItemInteraction? = null
    fun setListener(listener: ForecastItemInteraction) {
        this.listener = listener
    }

}