package com.example.weatherapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherFavorite")
data class MapModel(
    @PrimaryKey
    @ColumnInfo(name = "latLong")
    var latLong : String,
    @ColumnInfo(name = "countryName")
    var countryName:String,
    @ColumnInfo(name = "adminArea")
    var adminArea:String = "",
    @ColumnInfo(name = "latitude")
    var latitude:Double,
    @ColumnInfo(name = "longitude")
    var longitude:Double)