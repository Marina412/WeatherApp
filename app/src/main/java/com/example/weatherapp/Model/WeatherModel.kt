package com.example.weatherapp.models


data class Root(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezoneOffset: Long,
    val current: Current,
    val hourly: List<Hourly>,
    val daily: List<Daily>,
    var alerts:ArrayList<Alerts>
)

data class Current(
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Long,
    val humidity: Long,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Long,
    val visibility: Long,
    val windSpeed: Double,
    val windDeg: Long,
    val weather: List<Weather>
    /* val windGust: Double,
     val pop: Double? = null*/
)

data class Weather(
    val id: Long,
    val main: Main,
    val description: String,
    val icon: String
)

data class Hourly(
    val dt: Long,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Long,
    val humidity: Long,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Long,
    val visibility: Long,
    val windSpeed: Double,
    val windDeg: Long,
    val weather: List<Weather>,
    val windGust: Double,
    val pop: Double? = null
)

enum class Main {
    Clear,
    Clouds
}

data class Daily(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val moonPhase: Double,
    val temp: Temp,
    val feelsLike: FeelsLike,
    val pressure: Long,
    val humidity: Long,
    val dewPoint: Double,
    val windSpeed: Double,
    val windDeg: Long,
    val windGust: Double,
    val weather: List<Weather>,
    val clouds: Long,
    val pop: Double,
    val rain: Double,
    val uvi: Double
)

data class FeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Temp(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Alerts(
    var sender_name:String,
    var event: String,
    var start: Int,
    var end: Int,
    var description:String,
    var tags:ArrayList<String>
)
