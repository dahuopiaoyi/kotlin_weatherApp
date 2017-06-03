package com.a552409677qq.weatherapp.domain

import com.a552409677qq.weatherapp.Forecast
import com.a552409677qq.weatherapp.ForecastResult
import java.text.DateFormat
import java.util.*
import com.a552409677qq.weatherapp.domain.Forecast as ModelForecast

/**
 * Created by 55240 on 2017/6/3.
 */
public class ForecastDataMapper{
    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country,
                convertForecastListToDomain(forecast.list))
    }
    private fun convertForecastListToDomain(list: List<Forecast>):
            List<ModelForecast> {
        return list.map {
            convertForecastListToDomain(it)
        }
    }
    private fun convertForecastListToDomain(forecast: Forecast):
            ModelForecast{
        return ModelForecast(convertData(forecast.dt),
                forecast.weather[0].description,
                forecast.temp.max.toInt(),
                forecast.temp.min.toInt())
    }
    private fun convertData(data: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM,
                Locale.getDefault())
        return df.format(data * 1000)
    }
}