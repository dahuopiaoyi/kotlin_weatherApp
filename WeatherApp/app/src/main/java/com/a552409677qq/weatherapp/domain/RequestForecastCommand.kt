package com.a552409677qq.weatherapp.domain

import com.a552409677qq.weatherapp.ForecastRequest

/**
 * Created by 55240 on 2017/6/3.
 * 调用openweather API并将数据解析成友好的内部数据
 */
class RequestForecastCommand(val zipCode: String): Command<ForecastList>{
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(
                forecastRequest.execute()
        )
    }
}