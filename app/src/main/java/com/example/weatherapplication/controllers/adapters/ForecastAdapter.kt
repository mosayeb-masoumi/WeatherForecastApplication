package com.example.weatherapplication.controllers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.controllers.interfaces.ForecastItemInteraction
import com.example.weatherapplication.controllers.viewholders.ForecastViewHolder
import com.example.weatherapplication.models.Forecastday
import javax.inject.Inject

class ForecastAdapter @Inject constructor() : RecyclerView.Adapter<ForecastViewHolder>(){

    private var list: List<Forecastday>? = ArrayList()

    fun setAdapterList(myList: List<Forecastday>?) {
        list = myList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val model = list?.get(position)
        model.let { holder.bindData(it) }
        holder.setOnListHolderListener(listener, model)
    }


    private var listener: ForecastItemInteraction? = null
    fun setListener(listener: ForecastItemInteraction) {
        this.listener = listener
    }


    override fun getItemCount(): Int {
        return  list!!.size
    }

}