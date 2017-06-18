package com.a552409677qq.weatherapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.a552409677qq.weatherapp.domain.ForecastList
import com.a552409677qq.weatherapp.domain.Forecast
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

/**
 * Created by loudj on 2017/6/18.
 * 使用lambdas重写ForecastListAdapter
 */

class ForecastListAdapterWithLambdas(val weekForecast: ForecastList,
                                     val itemClick: (Forecast) -> Unit):
        RecyclerView.Adapter<ForecastListAdapterWithLambdas.ViewHolder>() {
    override fun getItemCount(): Int = weekForecast.size()

    //绑定ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    //创建ViewHolder，使用了本类中的重写的ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast,
                parent, false)
        return ForecastListAdapterWithLambdas.ViewHolder(view, itemClick)
    }

    class ViewHolder(val view: View, val itemClick: (Forecast) -> Unit): RecyclerView.ViewHolder(view) {
        private val iconView: ImageView
        private val dateView: TextView
        private val descriptionView: TextView
        private val maxTemperatuerView: TextView
        private val minTemperatureView: TextView
        init {
            iconView = view.find(R.id.icon)
            dateView = view.find(R.id.date)
            descriptionView = view.find(R.id.description)
            maxTemperatuerView = view.find(R.id.maxTemperature)
            minTemperatureView = view.find(R.id.minTemperature)
        }
        fun bindForecast(forecast: com.a552409677qq.weatherapp.domain.Forecast) {
            with(forecast) {
                Picasso.with(itemView.context).load(iconUrl).into(iconView)
                dateView.text = data
                descriptionView.text = description
                maxTemperatuerView.text = "${high.toString()}"
                minTemperatureView.text = "${low.toString()}"
                itemView.setOnClickListener{ itemClick(forecast) }
            }
        }
    }
}
