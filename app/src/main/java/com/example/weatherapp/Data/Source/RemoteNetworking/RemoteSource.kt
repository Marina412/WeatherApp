package com.example.weatherapp.Data.Source.RemoteNetworking

import com.example.weatherapp.Data.DataSource
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Model.Root
import kotlinx.coroutines.flow.Flow

class RemoteSource(private val apiInterface: APIInterface): DataSource {
    override suspend fun getWeatherDataCurrentStander(
        latitude: Double,
        longitude: Double,
        exclude: String,
        apiKey: String,
        lang: String
    ): Root {
       return apiInterface.getWeatherDataCurrentStander(latitude,longitude,exclude,apiKey,lang)
    }

    override suspend fun insertLastResponse(roomWeatherModel: RoomWeatherModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getLastResponseFromRoom(): RoomWeatherModel {
        TODO("Not yet implemented")
    }

    override suspend fun insertToFavorite(favModel: MapModel) {
        TODO("Not yet implemented")
    }

    override fun getFromFavorite(): Flow<List<MapModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromFavorite(favModel: MapModel) {
        TODO("Not yet implemented")
    }

    override suspend fun insertToAlerts(roomAlertsModel: RoomAlertsModel) {
        TODO("Not yet implemented")
    }

    override fun getFromAlerts(): Flow<List<RoomAlertsModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromAlerts(roomAlertsModel: RoomAlertsModel) {
        TODO("Not yet implemented")
    }
}