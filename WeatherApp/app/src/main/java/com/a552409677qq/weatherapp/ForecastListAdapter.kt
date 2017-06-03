package com.a552409677qq.weatherapp

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.a552409677qq.weatherapp.domain.ForecastList
import org.w3c.dom.Text

/**
 * Created by loudj on 2017/6/2.
 */
class ForecastListAdapter(val weekForecast: ForecastList):
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        return ViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text =  "${weekForecast.city}"
        with(weekForecast[position]) {
            holder.textView.text = "${holder.textView.text} - $data - $description - $high/$low"
        }
    }

    override fun getItemCount(): Int = weekForecast.size()

    class ViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
}
