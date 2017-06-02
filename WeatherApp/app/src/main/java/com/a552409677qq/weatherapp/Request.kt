package com.a552409677qq.weatherapp

import android.util.Log
import org.jetbrains.anko.longToast
import java.net.URL

/**
 * Created by loudj on 2017/6/2.
 */
public class Request(val url: String){
    public fun run() {
        val forecastJsonStr = URL(url).readText()
        Log.d(javaClass.simpleName, forecastJsonStr)
    }
}