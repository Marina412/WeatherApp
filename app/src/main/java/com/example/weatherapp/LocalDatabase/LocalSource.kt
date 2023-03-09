package com.example.weatherapp.LocalDatabase
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomWeatherModel
import kotlinx.coroutines.flow.Flow


interface LocalSource {
    //weather
    suspend fun insertLastResponse(roomWeatherModel: RoomWeatherModel)
    suspend fun getLastResponseFromRoom() : RoomWeatherModel

    //fav
    suspend fun insertToFavorite(favModel: MapModel)
    fun getFromFavorite() : Flow<List<MapModel>>
    suspend fun deleteFromFavorite(favModel: MapModel)



}