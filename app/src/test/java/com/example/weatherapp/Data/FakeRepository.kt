package com.example.weatherapp.Data

import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Model.Root
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository(

    private var favList:MutableList<MapModel> = mutableListOf<MapModel>(),
    private var alarmList:MutableList<RoomAlertsModel> = mutableListOf<RoomAlertsModel>(),
    private var response:RoomWeatherModel= RoomWeatherModel(weather = Root()),
    private var weather:Root=Root()

):RepositoryInterface{

    override fun getAllResponseFromAPI(lat: Double,
                              lon: Double,
                              lang: String) :
            Flow<Root> = flow{
                          emit(weather)
    }

    override suspend fun insertLastResponse(roomWeatherModel: RoomWeatherModel) {
        response=roomWeatherModel
    }

    override suspend fun getLastResponseFromDB(): Flow<RoomWeatherModel> = flow  {
        emit( response)
    }

    override suspend fun insertToFavorite(favModel: MapModel) {
        favList.add(favModel)
    }

    override fun getAllFavoriteFromRoom(): Flow<List<MapModel>> = flow {
        emit(favList)
    }

    override suspend fun deleteFromFavorite(favModel: MapModel) {
        favList.remove(favModel)
    }

    override suspend fun insertToAlerts(roomAlertsModel: RoomAlertsModel) {
        alarmList.add(roomAlertsModel)
    }

    override fun getFromAlerts(): Flow<List<RoomAlertsModel>> = flow  {
        emit(alarmList)
    }

    override suspend fun deleteFromAlerts(roomAlertsModel: RoomAlertsModel) {
        alarmList.remove(roomAlertsModel)
    }
    override suspend fun getCurrentWeatherForWorker(lat: Double, lon: Double): Root {
        return weather
    }

}