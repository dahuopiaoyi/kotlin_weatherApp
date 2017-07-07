package com.a552409677qq.weatherapp.SqliteHelper

import android.database.sqlite.SQLiteDatabase
import android.provider.MediaStore
import android.provider.Telephony
import com.a552409677qq.weatherapp.domain.Forecast
import com.a552409677qq.weatherapp.domain.ForecastDataMapper
import com.a552409677qq.weatherapp.domain.ForecastList
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.insert

/**
 * Created by loudj on 2017/7/5.
 */

fun <T: Any> SelectQueryBuilder.parseList(
        parser: (Map<String, Any>) -> T): List<T> =
            parseList(object : MapRowParser<T> {
                override fun parseRow(columns: Map<String, Any>): T
                    = parser(columns)
            })

fun SQLiteDatabase.clear(tableName: String) {
    execSQL("delete from $tableName")
}

fun <K, V: Any> MutableMap<K, V?>.toVarargArray():
    Array<out Pair<K, V>> = map({
    Pair(it.key, it.value!!)
}).toTypedArray()

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

    fun saveForecast(forecast: ForecastList) = forecastDBHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())

            dailyForecast.forEach {
                insert(DayForecastTable.NAME, *it.map.toVarargArray())
            }
        }
    }
}

class DBDataMapper {
    private fun convertDayToDomain(dayForecast: DayForecast) = with(
            dayForecast) {
        Forecast(date, description, high, low, iconUrl)
    }

    fun convertToDomain(forecast: CityForcast) = with(forecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id, city, country, daily)
    }

    fun convertFromDomain(forecast: ForecastList) = with(forecast) {
        val daily = dailyForecast.map {
            convertDayFromDomain(cityId, it)
        }
        CityForcast(cityId, city, country, daily)
    }

    private fun convertDayFromDomain(cityId: Long, forecast: Forecast) =
            with(forecast) {
                DayForecast(data, description, high, low, iconUrl, cityId)
            }
}