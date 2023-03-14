package com.example.weatherapp.Utils

import com.example.weatherapp.Model.RoomAlertsModel

sealed class ApiStateAlerts{
    class Success(val dataState:List<RoomAlertsModel>) : ApiStateAlerts()
    class Failure(val msg: Throwable) : ApiStateAlerts()
    object Loading : ApiStateAlerts()
}