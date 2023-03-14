package com.example.weatherapp.Data
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Model.Root
import com.example.weatherapp.Utils.Constants
import kotlinx.coroutines.flow.Flow


interface DataSource {


    //networking
    suspend fun getWeatherDataCurrentStander(
        latitude: Double,
        longitude: Double,
        exclude: String = Constants.EXCLUDE,
        apiKey: String = Constants.WEATHER_API_KEY,
        lang: String= Constants.ENGLISH
    ): Root

    //weather
    suspend fun insertLastResponse(roomWeatherModel: RoomWeatherModel)
    suspend fun getLastResponseFromRoom() : RoomWeatherModel

    //fav
    suspend fun insertToFavorite(favModel: MapModel)
    fun getFromFavorite() : Flow<List<MapModel>>
    suspend fun deleteFromFavorite(favModel: MapModel)

    //alerts
    suspend fun insertToAlerts(roomAlertsModel: RoomAlertsModel)

    fun getFromAlerts() : Flow<List<RoomAlertsModel>>

    suspend  fun deleteFromAlerts(roomAlertsModel: RoomAlertsModel)




}