package com.a552409677qq.weatherapp.SqliteHelper

import android.provider.MediaStore
import android.provider.Telephony
import com.a552409677qq.weatherapp.domain.Forecast
import com.a552409677qq.weatherapp.domain.ForecastDataMapper
import com.a552409677qq.weatherapp.domain.ForecastList
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder
import org.jetbrains.anko.db.select

/**
 * Created by loudj on 2017/7/5.
 */

fun <T: Any> SelectQueryBuilder.parseList(
        parser: (Map<String, Any>) -> T): List<T> =
            parseList(object : MapRowParser<T> {
                override fun parseRow(columns: Map<String, Any>): T
                    = parser(columns)
            })

public fun <T: Any> SelectQueryBuilder.parseOpt(
        parser: (Map<String, Any>) -> T): T? =
            parseOpt(object : MapRowParser<T> {
                override fun parseRow(columns: Map<String, Any>): T = parser(columns)
            })

class ForecastDB(
        val forecastDBHelper: ForecastDBHelper = ForecastDBHelper.instance,
        val dataMapper: DBDataMapper = DBDataMapper()) {
    fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDBHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND " +
                "${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }
        val city = select(DayForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt{ CityForcast(HashMap(it), dailyForecast) }

        if (city != null) dataMapper.convertToDomain(city) else null
    }
}

class DBDataMapper {
    private fun convertDayToDomain(dayForecast: DayForecast) = with(
            dayForecast) {
        Forecast(date, description, high, low, iconUrl)
    }

    fun convertToDomain(forecast: CityForcast) = with(forecast) {
        val daily = dailyForcast.map { convertDayToDomain(it) }
        ForecastList(, city, country, daily)
    }
}