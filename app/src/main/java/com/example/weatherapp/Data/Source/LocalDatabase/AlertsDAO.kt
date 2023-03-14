package com.example.weatherapp.Data.Source.LocalDatabase

import androidx.room.*
import com.example.weatherapp.Model.RoomAlertsModel

import kotlinx.coroutines.flow.Flow
@Dao
interface AlertsDAO {

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insertToAlerts(roomAlertsModel: RoomAlertsModel)

        @Query("SELECT * FROM alerts")
        fun getFromAlerts() : Flow<List<RoomAlertsModel>>

        @Delete
        fun deleteFromAlerts(roomAlertsModel: RoomAlertsModel)

    }