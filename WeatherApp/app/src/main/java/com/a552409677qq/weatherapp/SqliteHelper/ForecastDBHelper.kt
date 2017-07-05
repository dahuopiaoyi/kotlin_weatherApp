package com.a552409677qq.weatherapp.SqliteHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.a552409677qq.weatherapp.App
import org.jetbrains.anko.db.*


/**
 * Created by loudj on 2017/6/30.
 */

object CityForecastTable {
    val NAME    = "CityForecast"
    val ID      = "_id"
    val CITY    = "city"
    val COUNTRY = "country"
}

object DayForecastTable {
    val NAME        = "DayForecast"
    val ID          = "_id"
    val DATE        = "date"
    val DESCRIPTION = "description"
    val HIGH        = "high"
    val LOW         = "low"
    val ICON_URL    = "iconUrl"
    val CITY_ID     = "cityId"
}

class ForecastDBHelper(ctx: Context = App.instance): ManagedSQLiteOpenHelper(App.instance, ForecastDBHelper.DB_NAME, null, ForecastDBHelper.DB_VERSION) {
    companion object {
        val DB_NAME = "forecast.db"
        val DB_VERSION = 1
        val instance: ForecastDBHelper by lazy { ForecastDBHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(CityForecastTable.NAME, true,
                CityForecastTable.ID to INTEGER + PRIMARY_KEY,
                CityForecastTable.CITY to TEXT,
                CityForecastTable.COUNTRY to TEXT)

        db.createTable(DayForecastTable.NAME, true,
                DayForecastTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                DayForecastTable.DATE to INTEGER,
                DayForecastTable.DESCRIPTION to TEXT,
                DayForecastTable.HIGH to INTEGER,
                DayForecastTable.LOW to INTEGER,
                DayForecastTable.ICON_URL to TEXT,
                DayForecastTable.CITY_ID to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(CityForecastTable.NAME, true)
        db.dropTable(DayForecastTable.NAME, true)
        onCreate(db)
    }
}