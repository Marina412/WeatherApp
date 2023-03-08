package com.example.weatherapp.LocalDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.Model.Converters
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomWeatherModel


@Database(entities = [RoomWeatherModel::class,MapModel::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDAO(): WeatherDAO
    abstract fun favDAO(): FavDAO

    companion object {
        private var INSTANCE: WeatherDataBase? = null
        //one thread at a time to access this method
        @Synchronized
        fun getInstance(context: Context): WeatherDataBase{
            return INSTANCE?: synchronized(this){
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
/*




abstract class  WeatherDataBase : RoomDatabase() {
    abstract fun weatherDAO(): WeatherDAO
}
object AppDatabase {
    private var INSTANCE: WeatherDataBase? = null
    fun getInstance(context: Context): WeatherDataBase {
        if (INSTANCE == null) {
            synchronized(WeatherDataBase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }
    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            WeatherDataBase::class.java,
            "WeatherRoom"
        ).build()
}
*/
