package com.example.weatherapp.Utils

import com.example.weatherapp.Model.MapModel


sealed class ApiStateFav {
    class Success(val dataState:List<MapModel>) : ApiStateFav()
    class Failure(val msg: Throwable) : ApiStateFav()
    object Loading : ApiStateFav()
}