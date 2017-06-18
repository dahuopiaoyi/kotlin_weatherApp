package com.a552409677qq.weatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.a552409677qq.weatherapp.GPSLocation.MyLocationListener
import com.a552409677qq.weatherapp.GPSLocation.MyLocationManager
import com.a552409677qq.weatherapp.domain.Forecast
import com.a552409677qq.weatherapp.domain.RequestForecastCommand
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
//为了可以使用anko提供的find方法

class MainActivity : AppCompatActivity() {
    private val LOCATIONPERMISSION_CODE = 1
    private lateinit var textView: TextView
    private lateinit var locationManager: LocationManager
    private var hasLocationPermission: Int = -1
    private lateinit var myLocationManager: MyLocationManager
    private lateinit var myLocationListener: MyLocationListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = find(R.id.location)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        myLocationManager = MyLocationManager(this, textView)
        myLocationListener = MyLocationListener(textView)

        //获取位置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATIONPERMISSION_CODE)
            }
            else {
               myLocationManager.setLocation()
            }
        }
        else {
            myLocationManager.setLocation()
        }


        val forecastList: RecyclerView = find(R.id.forecast_list)
//        forecastList.addOnScrollListener()

        forecastList.layoutManager = LinearLayoutManager(this)
        async {
            uiThread { longToast("start Requset") }
            val result =
                RequestForecastCommand("Beijing,CN").execute()
            uiThread {
                //使用ForecastListAdapter
//                forecastList.adapter = ForecastListAdapter(result,
//                        object: ForecastListAdapter.OnItemClickListener{
//                            override fun invoke(forecast: Forecast) {
//                                toast(forecast.data)
//                            }
//                        })
                //使用ForcastListAdapterWithLambdas
                forecastList.adapter = ForecastListAdapterWithLambdas(result) { toast(it.data) }
            }
            uiThread { longToast("Request Success") }
        }
    }

    override fun onResume() {
        super.onResume()
        if (hasLocationPermission == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0F, myLocationListener)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0F, myLocationListener)
        }
    }

    override fun onPause() {
        super.onPause()
        if (hasLocationPermission == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(myLocationListener)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATIONPERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                myLocationManager.setLocation()
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0F, myLocationListener)
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0F, myLocationListener)
            }
        }
    }
}
