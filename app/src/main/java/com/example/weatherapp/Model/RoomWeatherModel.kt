package com.example.weatherapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.models.Root

@Entity(tableName = "weatherResponse")
data class RoomWeatherModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id:Int?=null,
    @ColumnInfo(name = "allresponse")
    val wether: Root


)
