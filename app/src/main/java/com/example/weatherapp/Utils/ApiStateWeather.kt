package com.example.weatherapp.Utils
import com.example.weatherapp.models.Root


sealed class ApiStateWeather {
    class Success(val data: Root) : ApiStateWeather()
    class Failure(val msg: Throwable) : ApiStateWeather()
    object Loading : ApiStateWeather()
}
