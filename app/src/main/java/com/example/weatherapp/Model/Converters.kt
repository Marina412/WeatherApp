package com.example.weatherapp.Model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.weatherapp.models.Root
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromStringToResponse(stringResponse: String)= Gson().fromJson(stringResponse, Root::class.java)
    @TypeConverter
    fun fromResponseToString(responseRoot: Root)=Gson().toJson(responseRoot)
}