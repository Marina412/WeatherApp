package com.example.weatherapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class RoomAlertsModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0,

    @ColumnInfo(name = "long")
    var lon:Double,

    @ColumnInfo(name = "lat")
    var lat:Double,
    @ColumnInfo(name = "edate")
    var endDate:String,

    @ColumnInfo(name = "time")
    var time:String,
    @ColumnInfo(name = "houre")
    var hour:Int,
    @ColumnInfo(name = "minute")
    var minute: Int,
    @ColumnInfo(name = "day")
    var startDay:Int,
    @ColumnInfo(name = "month")
    var startMonth: Int,
    @ColumnInfo(name = "year")
    var startYear: Int
)