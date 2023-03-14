package com.example.weatherapp.Data

import android.app.Application
import com.example.weatherapp.Data.Source.LocalDatabase.LocalSource
import com.example.weatherapp.Data.Source.LocalDatabase.WeatherDataBase
import com.example.weatherapp.Data.Source.RemoteNetworking.APIClient
import com.example.weatherapp.Data.Source.RemoteNetworking.RemoteSource
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Model.Root
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository //private constructor
                                     (private val remoteSource : DataSource,
                                     private val localSource: DataSource,
                                    // private val sharedPreferences: SharedPreferences,
                                    // private val context: Context
                                     //private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
                                     ): RepositoryInterface {


    companion object {
        private var instance: Repository? = null
        fun getInstance(
            /*remoteSource: DataSource,
            localSource: DataSource,
            sharedPreferences: SharedPreferences,
            context: Context*/
        app: Application

        ): Repository {
            return instance ?: synchronized(this) {

                val apiInterface= APIClient.getInstane()
                val remoteSource= RemoteSource(apiInterface)

                val database= WeatherDataBase.getInstance(app)

                val favDAO=database.favDAO()
                val weatherDAO=database.weatherDAO()
                val alertsDAO=database.alertsDAO()
                val localSource= LocalSource(weatherDAO, favDAO, alertsDAO)


                Repository(remoteSource, localSource)//, app)
            }
        }
    }






    override fun getAllResponseFromAPI(lat: Double, lon: Double, lang: String): Flow<Root> = flow {
        emit(
            remoteSource.getWeatherDataCurrentStander(
                latitude = lat,
                longitude = lon,
                lang = lang
            )
        )


    }

    override suspend fun insertLastResponse(weatherModel: RoomWeatherModel) =localSource.insertLastResponse(weatherModel)

    override suspend fun getLastResponseFromDB(): Flow<RoomWeatherModel> = flow {
        emit(localSource.getLastResponseFromRoom())
    }
///////fav
    override suspend fun insertToFavorite(favModel: MapModel) = localSource.insertToFavorite(favModel)

    override  fun getAllFavoriteFromRoom()=localSource.getFromFavorite()

    override suspend fun deleteFromFavorite(favModel: MapModel) =localSource.deleteFromFavorite(favModel)

    ///alerts
    override suspend fun insertToAlerts(roomAlertsModel: RoomAlertsModel)=localSource.insertToAlerts(roomAlertsModel)

    override fun getFromAlerts()=localSource.getFromAlerts()

    override suspend fun deleteFromAlerts(roomAlertsModel: RoomAlertsModel) =localSource.deleteFromAlerts(roomAlertsModel)
    override suspend fun getCurrentWeatherForWorker(lat: Double, lon: Double)= remoteSource.getWeatherDataCurrentStander(latitude=lat,longitude =lon)



}