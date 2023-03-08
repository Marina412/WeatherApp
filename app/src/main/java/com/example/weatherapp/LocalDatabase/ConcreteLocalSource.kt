package com.example.weatherapp.LocalDatabase

import android.content.Context
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomWeatherModel

class ConcreteLocalSource(context: Context) : LocalSource {
    //todo DAO FROM DATA CLASS TO HERE
    private val weatherDAO : WeatherDAO?
    private  val favDAO: FavDAO?
    init {
        val database: WeatherDataBase = WeatherDataBase.getInstance(context)
        favDAO=database.favDAO()
        weatherDAO=database.weatherDAO()
    }

/////////////////Weather

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


  /*  override suspend fun getItemFromFav(countryName: String): MapModel {
        return favDAO!!.getItemFromFav(countryName)
    }
*/
    override suspend fun deleteFromFavorite(favModel: MapModel) {
        favDAO?.deleteFromFavorite(favModel)
    }




}