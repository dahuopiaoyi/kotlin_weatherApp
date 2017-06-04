package com.a552409677qq.weatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.a552409677qq.weatherapp.domain.Forecast
import com.a552409677qq.weatherapp.domain.RequestForecastCommand
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
//为了可以使用anko提供的find方法

class MainActivity : AppCompatActivity() {

//    private val items = listOf(
//            "Mon 6/23 - Sunny - 31/17",
//            "Tue 6/24 - Foggy - 21/8",
//            "Mon 6/23 - Sunny - 31/17",
//            "Tue 6/24 - Foggy - 21/8",
//            "Mon 6/23 - Sunny - 31/17",
//            "Tue 6/24 - Foggy - 21/8",
//            "Mon 6/23 - Sunny - 31/17"
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val forecastList: RecyclerView = find(R.id.forecast_list)
//        val forecastList = findViewById(R.id.forecast_list) as RecyclerView
        forecastList.layoutManager = LinearLayoutManager(this)
//        forecastList.adapter = ForecastListAdapter(forecastList)
//        message.text = "hello kotlin"
//        val url = "http://www.weather.com.cn/data/sk/101010100.html"
        async {
            uiThread { longToast("start Requset") }
            val result =
                RequestForecastCommand("Beijing,CN").execute()
//                RequestForecastCommand("94043").execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter(result,
                        object: ForecastListAdapter.OnItemClickListener{
                            override fun invoke(forecast: Forecast) {
                                toast(forecast.data)
                            }
                        })
            }

//            Request(url).run()
            uiThread { longToast("Request Success") }
        }
    }
}
