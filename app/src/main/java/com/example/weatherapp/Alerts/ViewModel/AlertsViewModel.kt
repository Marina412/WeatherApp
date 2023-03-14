package com.example.weatherapp.Alerts.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Data.RepositoryInterface
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.Utils.ApiStateAlerts
import com.example.weatherapp.Utils.ApiStateWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AlertsViewModel(val repository: RepositoryInterface): ViewModel() {


    private var _data = MutableStateFlow<ApiStateAlerts>(ApiStateAlerts.Loading)
    var data = _data.asStateFlow()
    //////////////////////////////////////////////////////////////////////////////////////////
    private var _dataApi = MutableStateFlow<ApiStateWeather>(ApiStateWeather.Loading)
    var dataApi = _dataApi.asStateFlow()
    /////////////////////////////////////////////


    private val iRepository: RepositoryInterface =repository

    init {
        getFromAlerts()
    }


    fun getAllWeatherStander(latitude: Double, longitude: Double, lang: String) =
        viewModelScope.launch {
            iRepository.getAllResponseFromAPI(latitude,longitude,lang).catch { e ->
                _dataApi.value = ApiStateWeather.Failure(e)
            }.collect { data ->
                _dataApi.value = ApiStateWeather.Success(data)
            }
        }




    fun insertToAlerts(roomAlertsModel: RoomAlertsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            iRepository.insertToAlerts(roomAlertsModel)
            //getFromAlerts()
        }
    }



    fun getFromAlerts() =
        viewModelScope.launch {
            //withContext(Dispatchers.IO) {
                iRepository.getFromAlerts().catch { e ->
                    _data.value = ApiStateAlerts.Failure(e)
                }.collect { data ->
                    _data.value = ApiStateAlerts.Success(data)
                }

           // }
        }



    fun deleteFromAlerts(roomAlertsModel: RoomAlertsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            iRepository.deleteFromAlerts(roomAlertsModel)
            //getFromAlerts()
        }
    }





}