package com.a552409677qq.weatherapp.GPSLocation

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.util.Log
import android.widget.TextView
import com.a552409677qq.weatherapp.MainActivity
import java.util.jar.Manifest

/**
 * Created by loudj on 2017/6/6.
 * 用于获取当前GPS位置信息
 */

//LocationListener 接口的实现，用于监听位置变化
class MyLocationListener(val textView: TextView): LocationListener {
    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(p0: Location?) {
        if (p0 != null) {
            textView.text = p0.toString()
        }
    }

    override fun onProviderDisabled(p0: String?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(p0: String?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class MyLocationManager(val context: Context, val textView: TextView) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    public fun setLocation(){
        var location: Location? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasLocationPermission = context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            if (hasLocationPermission != PackageManager.PERMISSION_DENIED) {
                //没有权限取得位置
            } else {
                textView.text = getLocation().toString()
            }
        } else {
            textView.text = getLocation().toString()
        }
    }

    private fun getLocation(): Location? {
        var location: Location? = null
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if ((location == null) and (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
        }
        return location
    }

}
