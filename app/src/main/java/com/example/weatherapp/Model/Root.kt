package com.example.weatherapp.Model

data class Root(
    val lat: Double?=null,
    val lon: Double?=null,
    val timezone: String?=null,
    val timezoneOffset: Long?=null,
    val current: Current?=null,
    val hourly: List<Hourly> = emptyList(),
    val daily: List<Daily> = emptyList(),
    var alerts:List<Alerts> = emptyList()
)