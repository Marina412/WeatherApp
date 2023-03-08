package com.example.weatherapp.Model

import androidx.lifecycle.LiveData
import com.example.weatherapp.models.Root
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositoryInterface{

    /*fun getAllResponseFromAPI(
        lat: Double,
        lon: Double,
        key: String,
        language: String,
        unit: String ) : Response<Root>*/
//weather
    fun getAllResponseFromAPI(lat: Double,
                              lon: Double,
                              exclude: String) : Flow<Root>

    suspend fun insertLastResponse(roomWeatherModel: RoomWeatherModel)
    suspend fun getLastResponseFromDB() : Flow<RoomWeatherModel>


    //fav
    suspend fun insertToFavorite(favModel: MapModel)

     fun getAllFavoriteFromRoom() : Flow<List<MapModel>>

    //suspend fun getItemFromFav(countryName:String): Flow<MapModel>
    suspend fun deleteFromFavorite(favModel: MapModel)

    ///////////////////////////////////////////////

    ////todo prefrance here




}