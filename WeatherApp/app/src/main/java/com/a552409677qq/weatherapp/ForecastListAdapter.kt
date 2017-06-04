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
//为了可以使用anko提供的find方法
import org.jetbrains.anko.find
import kotlinx.android.synthetic.main.item_forecast.*
import org.w3c.dom.Text

/**
 * Created by loudj on 2017/6/2.
 * recyclerView的适配器，用于将数据显示在view上
 */

class ForecastListAdapter(val weekForecast: ForecastList,
                          val itemClick: ForecastListAdapter.OnItemClickListener):
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>(){
    public interface OnItemClickListener {
        operator fun invoke(forecast: Forecast)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast,
                parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
     //   holder.textView.text =  "${weekForecast.city}"
//        with(weekForecast[position]) {
//            holder.textView.text = "${holder.textView.text} - $data - $description - $high/$low"
//        }
    }

    override fun getItemCount(): Int = weekForecast.size()

//    class ViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
    class ViewHolder(view: View, val itemClick: OnItemClickListener):
        RecyclerView.ViewHolder(view) {
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
    fun bindForecast(forecast: Forecast) {
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