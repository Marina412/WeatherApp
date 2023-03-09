package com.example.weatherapp.Home.ViewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Model.Repository
import com.example.weatherapp.Model.RepositoryInterface
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Utils.ApiStateWeather
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.UtilsFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel(val repository: RepositoryInterface): ViewModel() {

    private var _data = MutableStateFlow<ApiStateWeather>(ApiStateWeather.Loading)
    var data = _data.asStateFlow()
//////////////////////////////////////////////////////////////////////////////////////////
private val iRepository:RepositoryInterface=repository

    fun getAllWeatherStander(latitude: Double, longitude: Double, exclude: String="minutely") =
        viewModelScope.launch {
        iRepository.getAllResponseFromAPI(latitude,longitude,exclude).catch { e ->
            _data.value = ApiStateWeather.Failure(e)
        }.collect { data ->
            ////////todo map withe shard
             _data.value = ApiStateWeather.Success(data)


        }
    }


    fun insertLastResponse(roomWeatherModel: RoomWeatherModel) {
        viewModelScope.launch(Dispatchers.IO) {
            iRepository.insertLastResponse(roomWeatherModel)
        }
    }


    fun getLastResponseFromRoom() =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                iRepository.getLastResponseFromDB().catch { e ->
                    _data.value = ApiStateWeather.Failure(e)
                }.collect { data ->

                    _data.value = ApiStateWeather.Success(data.wether)
                }

            }
        }


}
