package com.example.weatherapp.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.R
import com.example.weatherapp.R.string
import com.google.android.gms.location.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class UtilsFunction {

companion object {
    fun getWeatherIcon(weatherIcon: String): Int {
        when (weatherIcon) {
            "01d", "01n" -> return R.drawable.clear_sky
            "02d", "02n" -> return R.drawable.few_clouds
            "03d", "03n" -> return R.drawable.scattered_clouds
            "04d", "04n" -> return R.drawable.broken_clouds
            "09d", "09n" -> return R.drawable.shower_rain
            "10d", "10n" -> return R.drawable.rain
            "11d", "11n" -> return R.drawable.thunderstorm
            "13d", "13n" -> return R.drawable.snow
            "5d", "5n" -> return R.drawable.mist
            else -> return R.drawable.sun_logo
        }
    }

    fun convertDateAndTimeToMills(timeAndDate: String?): Long {
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm")
        val timeInMilliseconds: Long
        var mDate: Date? = null
        try {
            mDate = sdf.parse(timeAndDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        Log.i("TAG", "Date m datee: $mDate")
        timeInMilliseconds = mDate!!.time
        println("Date in milli :: $timeInMilliseconds")
        return timeInMilliseconds
    }


    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }


    fun convertFromKelvinToCelsius(tempKelvin: Double): Double {
        val tempCelsius = tempKelvin - 273.15f
        return tempCelsius

    }

    fun convertFromKelvinToFahrenheit(tempKelvin: Double): Double {
        val tempFahrenheit = 1.8 * (tempKelvin - 273) + 32
        return tempFahrenheit

    }


    fun convertMeterspersecToMilesperhour(metersPerSec: Double): Double {
        val milesPerHour = metersPerSec * 2.23694

        return milesPerHour
    }

/*

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(
        dt: Long,
        timezone: String,
        format: String = "EEE, MMMM d K:mm a"
    ): String {

        val zoneId = ZoneId.of(timezone)
        val instant = Instant.ofEpochSecond(dt)
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
        return instant.atZone(zoneId).format(formatter)
    }
*/


    @RequiresApi(Build.VERSION_CODES.O)
    fun getDay(dt: Long, timezone: String, format: String = "EEE"): String {

        val zoneId = ZoneId.of(timezone)
        val instant = Instant.ofEpochSecond(dt)
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
        return instant.atZone(zoneId).format(formatter)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTime(dt: Long, timezone: String, format: String = "K:mm a"): String {

        val zoneId = ZoneId.of(timezone)
        val instant = Instant.ofEpochSecond(dt)
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
        return instant.atZone(zoneId).format(formatter)
    }

/*
    fun convertTimeInMillesToMinutes(time: Long): String {
        val myDate = Date(time)
        val formatter = SimpleDateFormat("hh:mm a")
        val myTime = formatter.format(myDate)
        return myTime
    }

    fun convertDateInMillsToDate(date: Long): String {
        val myDate = Date(date)
        val formatter = SimpleDateFormat("dd/M/yyyy")
        val myTime = formatter.format(myDate)
        return myTime
    }*/
//todo

/*

    private fun getAddressFromLocation(latitude: Double, longitude: Double, context: Context): String {
        val addresses: List<Address>
        var address = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
            address = addresses[0].getAddressLine(0)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return address
    }
*/

    @SuppressLint("MissingPermission")
    fun getAddressFromLocation(activity: Activity, getMyLocation: (location: Location) -> Unit) {


        var mFusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity)

        val mLocationRequest = LocationRequest()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(0)


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, object : LocationCallback() {

            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            override fun onLocationResult(locationResult: LocationResult) {
                val mLastLocation: Location? = locationResult.getLastLocation()
                getMyLocation(mLastLocation!!)
            }

        }, Looper.myLooper())

    }

   fun getFullAddress(lat:Double, lon:Double,context: Context): String {
        val geocoder= Geocoder(context, Locale.getDefault())
        val addresses=geocoder.getFromLocation(lat,lon,1)
        return addresses?.get(0)?.getAddressLine(0).toString()
    }

    fun getAddress(context: Context,lat:Double, lon:Double)=Geocoder(context).
    getFromLocation(lat,lon,5)?.get(0)


     fun showDialog(title:String,message:String ,alertButtonResult: AlertButtonResult, mapModel: MapModel,context: Context) {
        val alertBuild: AlertDialog.Builder = AlertDialog.Builder(context)
        alertBuild.setTitle(title)
        alertBuild.setMessage(message)
        alertBuild.setPositiveButton(string.ok) {
                _, _ ->
            Toast.makeText(context, "${context.getString(string.message_Location_saved)}  $mapModel ", Toast.LENGTH_LONG).show()

           alertButtonResult.IfOk(mapModel)
        }
        alertBuild.setNegativeButton(string.cancel) {
                _, _ ->
            Toast.makeText(context,context.getString(string.message_Location_canceld), Toast.LENGTH_SHORT).show()
        }
        val alert = alertBuild.create()
        alert.show()
    }




}




}