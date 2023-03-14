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


){
    @Ignore
    var started: Boolean=false
}

/*
@PrimaryKey(autoGenerate = true)
@ColumnInfo(name = "id")
var id:Int? = null,

@ColumnInfo(name = "long")
var lon:Double,

@ColumnInfo(name = "lat")
var lat:Double,

@ColumnInfo(name = "sdate")
var startDate:Int,

@ColumnInfo(name = "edate")
var endDate:Int,

@ColumnInfo(name = "time")
var time:String,
@ColumnInfo(name = "houre")
var hour:Int,
@ColumnInfo(name = "minute")
var minute: Int,

@ColumnInfo(name = "month")
var month: Int,
@ColumnInfo(name = "year")
var year: Int ,

@ColumnInfo(name = "started")
var started: Boolean ,
@ColumnInfo(name = "created")
val created: Long*/
