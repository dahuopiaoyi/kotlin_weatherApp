package com.a552409677qq.weatherapp.domain

import java.text.FieldPosition

/**
 * Created by 55240 on 2017/6/3.
 * 将json数据解析为更友善的数据格式，用于界面显示
 */
public interface Command<T> {
    fun execute(): T
}

data class ForecastList(val city: String, val country: String,
                        val dailyForecast: List<Forecast>) {
    operator fun get(position: Int): Forecast =
            dailyForecast[position]
    fun size(): Int = dailyForecast.size
}

data class Forecast(val data: String, val description: String,
                    val high: Int, val low: Int, val iconUrl: String)