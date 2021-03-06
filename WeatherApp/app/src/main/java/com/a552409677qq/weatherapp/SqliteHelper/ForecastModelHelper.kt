package com.a552409677qq.weatherapp.SqliteHelper

/**
 * Created by loudj on 2017/7/5.
 */

class CityForcast(val map: MutableMap<String, Any?>,
                  val dailyForecast: List<DayForecast>)
{
    var _id: Long by map
    var city: String by map
    var country: String by map

    constructor(id: Long, city: String, country: String,
                dailyForcast: List<DayForecast>)
    : this(HashMap(), dailyForcast) {
        this._id = id
        this.city = city
        this.country = country
    }
}

class DayForecast(var map: MutableMap<String, Any?>) {
    var _id: Long by map
    var date: String by map
    var description: String by map
    var high: Int by map
    var low: Int by map
    var iconUrl: String by map
    var cityId: Long by map

    constructor(date: String, description: String, high: Int, low: Int,
                iconUrl: String, cityId: Long)
    : this(HashMap()) {
        this.date = date
        this.description = description
        this.high = high
        this.low = low
        this.iconUrl = iconUrl
        this.cityId = cityId
    }
}