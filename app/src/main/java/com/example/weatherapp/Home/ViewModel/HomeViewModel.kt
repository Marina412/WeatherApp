package com.example.weatherapp.Home.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Model.RepositoryInterface
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Utils.ApiStateWeather
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
//////////////////////////////////////////////////////////////


    init {
        getAllWeatherStander(latitude=33.44,
            longitude =-94.04,
            exclude="minutely")
    }
    fun getAllWeatherStander(latitude: Double, longitude: Double, exclude: String) = viewModelScope.launch {

        iRepository.getAllResponseFromAPI(latitude,longitude,exclude).catch { e ->
            _data.value = ApiStateWeather.Failure(e)
        }.collect { data ->
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
