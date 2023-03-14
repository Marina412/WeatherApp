package com.example.weatherapp.Utils

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.R
import com.google.android.gms.location.*
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

interface UtilsFunction {

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
            "50d", "50n" -> return R.drawable.mist
            else -> return R.drawable.sun_logo
        }
    }

    fun getArabicDayName(dayName: String): String {
        when (dayName) {
            "Sat" -> return "السبت"
            "Sun" -> return "الاحد"
            "Mon" -> return "الاثنين"
            "Tue" -> return "الخميس"
            "Wed" -> return "الاربعاء"
            "Thu" -> return "الثلاثاء"
            "Fri" -> return "الجمعة"
            else->return " "
        }
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


    fun convertFromKelvinToCelsius(tempKelvin: Double): String {
        val tempCelsius = tempKelvin - 273.15f
        return String.format(Locale.US, "%.1f", tempCelsius)

    }

    fun convertFromKelvinToFahrenheit(tempKelvin: Double): String{
        val tempFahrenheit = 1.8 * (tempKelvin - 273) + 32
        return String.format(Locale.US, "%.1f", tempFahrenheit)

    }




    fun convertMeterspersecToMilesperhour(metersPerSec: Double): Double {
        val milesPerHour = metersPerSec * 2.23694

        return milesPerHour
    }


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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(
        dt: Long,
        timezone: String,
        format: String = "EEE,MMMM d y"
    ): String {

        val zoneId = ZoneId.of(timezone)
        val instant = Instant.ofEpochSecond(dt)
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
        return instant.atZone(zoneId).format(formatter)
    }


   fun getFullAddress(lat:Double, lon:Double,context: Context): String {
        val geocoder= Geocoder(context, Locale.getDefault())
        val addresses=geocoder.getFromLocation(lat,lon,1)
        return if (addresses?.isNotEmpty() == true) addresses[0]!!.getAddressLine(0).toString() else ""
    }



    fun changeLanguage(lang:String, context: Context){
        val config = context.resources.configuration
        val locale = Locale(lang)
        Locale.setDefault(locale)
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        context.startActivity(Intent(context, context::class.java))

    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}




}