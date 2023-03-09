package com.example.weatherapp.LocalDatabase
import androidx.room.*

import com.example.weatherapp.Model.RoomWeatherModel


@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastResponse(roomWeatherModel: RoomWeatherModel)

    @Query("SELECT * FROM weatherResponse")
    fun getLastResponse() : RoomWeatherModel





}