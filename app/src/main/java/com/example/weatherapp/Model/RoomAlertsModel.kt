package com.example.weatherapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "alerts")
data class RoomAlertsModel(

    @PrimaryKey
    var id:String,
    @ColumnInfo(name = "long")
    var lon:Double,
    @ColumnInfo(name = "lat")
    var lan:Double,
    @ColumnInfo(name = "sdate")
    var startDate:String,
    @ColumnInfo(name = "edate")
    var endDate:String,
    @ColumnInfo(name = "time")
    var time:Double

)
