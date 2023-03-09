package com.example.weatherapp.Model

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherapp.LocalDatabase.LocalSource
import com.example.weatherapp.Networking.RemotSource
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.models.Root
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

private const val TAG = "Repository"
class Repository private constructor(var localSource : LocalSource,
                                     var remotSource: RemotSource,
                                     var sharedPreferences: SharedPreferences
                                     ): RepositoryInterface {


    companion object {
        private var instance: Repository? = null
        fun getInstance(
            localSource: LocalSource,
            remotSource: RemotSource,
            sharedPreferences: SharedPreferences

        ): Repository {
            return instance ?: Repository(
                localSource,remotSource,sharedPreferences
            )
        }
    }






    override fun getAllResponseFromAPI(lat: Double, lon: Double, exclude: String): Flow<Root> = flow {
        emit(
            remotSource.getWeatherDataCurrentStander(
                latitude=lat,
                longitude =lon,
                exclude="minutely"))


    }

    override suspend fun insertLastResponse(weatherModel: RoomWeatherModel) =localSource.insertLastResponse(weatherModel)

    override suspend fun getLastResponseFromDB(): Flow<RoomWeatherModel>  = flow {
        emit(localSource.getLastResponseFromRoom())
    }
///////fav
    override suspend fun insertToFavorite(favModel: MapModel) = localSource.insertToFavorite(favModel)

    override  fun getAllFavoriteFromRoom()=localSource.getFromFavorite()

    override suspend fun deleteFromFavorite(favModel: MapModel) =localSource.deleteFromFavorite(favModel)



}