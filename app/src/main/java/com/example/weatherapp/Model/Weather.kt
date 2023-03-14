package com.example.weatherapp.Model


data class Weather(
    val id: Long,
    val main: Main,
    val description: String,
    val icon: String
)