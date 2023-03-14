package com.example.weatherapp.Data.Source.LocalDatabase

import com.example.weatherapp.Data.DataSource
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Model.Root

class LocalSource(private val weatherDAO : WeatherDAO,
                  private val favDAO: FavDAO,
                  private val alertsDAO : AlertsDAO

   ) : DataSource {
    override suspend fun getWeatherDataCurrentStander(
        latitude: Double,
        longitude: Double,
        exclude: String,
        apiKey: String,
        lang: String
    ): Root {
        TODO("Not yet implemented")
    }

    override suspend fun insertLastResponse(roomWeatherModel: RoomWeatherModel) {
        weatherDAO?.insertLastResponse(roomWeatherModel)
    }

    override suspend fun getLastResponseFromRoom(): RoomWeatherModel {
       return  weatherDAO!!.getLastResponse()
    }



//////////////fav

    override suspend fun insertToFavorite(favModel: MapModel) {
        favDAO?.insertToFavorite(favModel)
    }

    override  fun getFromFavorite()=
        favDAO!!.getFromFavorite()


    override suspend fun deleteFromFavorite(favModel: MapModel) {
        favDAO?.deleteFromFavorite(favModel)
    }


////alerts
    override suspend fun insertToAlerts(roomAlertsModel: RoomAlertsModel) {
        alertsDAO?.insertToAlerts(roomAlertsModel)
    }

    override fun getFromAlerts() =alertsDAO!!.getFromAlerts()

    override suspend fun deleteFromAlerts(roomAlertsModel: RoomAlertsModel) {
       alertsDAO?.deleteFromAlerts(roomAlertsModel)
    }


}