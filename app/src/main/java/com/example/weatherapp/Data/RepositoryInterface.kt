package com.example.weatherapp.Data

import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Model.Root
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface{


//weather
    fun getAllResponseFromAPI(lat: Double,
                              lon: Double,
                              lang: String) : Flow<Root>

    suspend fun insertLastResponse(roomWeatherModel: RoomWeatherModel)
    suspend fun getLastResponseFromDB() : Flow<RoomWeatherModel>


    //fav
    suspend fun insertToFavorite(favModel: MapModel)

     fun getAllFavoriteFromRoom() : Flow<List<MapModel>>

    suspend fun deleteFromFavorite(favModel: MapModel)

    ///////////////////////////////////////////////

    ////todo prefrance here
    //alerts

    suspend fun insertToAlerts(roomAlertsModel: RoomAlertsModel)

    fun getFromAlerts() : Flow<List<RoomAlertsModel>>

    suspend  fun deleteFromAlerts(roomAlertsModel: RoomAlertsModel)
////////////////////////////////////////////////////////////////////////////
    suspend fun getCurrentWeatherForWorker(
        lat: Double,
        lon: Double
        ) : Root






}