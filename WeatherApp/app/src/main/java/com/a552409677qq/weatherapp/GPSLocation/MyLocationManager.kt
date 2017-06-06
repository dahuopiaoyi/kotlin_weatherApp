package com.a552409677qq.weatherapp.GPSLocation

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log

/**
 * Created by loudj on 2017/6/6.
 * 用于获取当前GPS位置信息
 */

class MyLocationManager(val context: Context) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    public fun myLocation(): String {
        var location: Location? = null
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            try {
//                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//            }
//            catch (e: ArithmeticException) {
//                throw IllegalStateException(e)
//            }
//
//        } else {

//        val locationListener = object : LocationListener {
//            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onProviderEnabled(p0: String?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onProviderDisabled(p0: String?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onLocationChanged(p0: Location?) {
//                if (location != null) {
//                    Log.e("Map", "Location changed : Lat:" +
//                            location.toString())
//                }
//            }
//        }
        val locationListener = MyLocationListener()
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1000, locationListener as LocationListener)
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
            return location.toString()
        }
//        }
        return "error"
    }
}

    class  MyLocationListener(): LocationListener {
        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onLocationChanged(p0: Location?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
