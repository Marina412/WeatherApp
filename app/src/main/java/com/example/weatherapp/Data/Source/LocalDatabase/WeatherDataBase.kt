package com.example.weatherapp.Data.Source.LocalDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.Model.*


@Database(entities = [RoomWeatherModel::class,MapModel::class,RoomAlertsModel::class], version = 10, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDAO(): WeatherDAO
    abstract fun favDAO(): FavDAO

    abstract fun alertsDAO(): AlertsDAO

    companion object {
        private var INSTANCE: WeatherDataBase? = null

        @Synchronized
        fun getInstance(context: Context): WeatherDataBase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDataBase::class.java,
                    "weater_table"
                )

                    .fallbackToDestructiveMigration().build()
                INSTANCE = db
                db
            }
        }
    }
}