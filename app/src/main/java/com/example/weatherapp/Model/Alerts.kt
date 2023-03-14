package com.example.weatherapp.Model


data class Alerts(
    var sender_name:String,
    var event: String,
    var start: Int,
    var end: Int,
    var description:String,
    var tags:ArrayList<String>
)