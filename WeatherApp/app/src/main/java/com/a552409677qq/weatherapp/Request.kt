package com.a552409677qq.weatherapp

import android.util.Log
import com.google.gson.Gson
import org.jetbrains.anko.longToast
import java.net.URL

/**
 * Created by loudj on 2017/6/2.
 * 用来请求openweather的天气数据，并将json数据转化为内部数据
 */
//public class Request(val url: String){
//    public fun run() {
//        val forecastJsonStr = URL(url).readText()
//        Log.d(javaClass.simpleName, forecastJsonStr)
//    }
//}
public class ForecastRequest(private val zipCode: String) {
    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val URL = "http://api.openweathermap.org/data/2.5/" +
                "forecast/daily?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "$URL&APPID=$APP_ID&q="
    }
    fun execute(): ForecastResult{
        val forecastJsonStr =
                URL(COMPLETE_URL + zipCode).readText()
        return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}