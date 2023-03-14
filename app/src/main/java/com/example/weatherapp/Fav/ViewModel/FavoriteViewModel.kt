package com.example.weatherapp.Fav.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Data.RepositoryInterface
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Utils.ApiStateFav
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(val repository: RepositoryInterface): ViewModel() {

    private var _data = MutableStateFlow<ApiStateFav>(ApiStateFav.Loading)

    var data = _data.asStateFlow()

//    private var _dataWeather = MutableStateFlow<ApiState>(ApiState.Loading)
//    var dataWeather = _dataWeather.asStateFlow()

    //////////////////////////////////////////////////////////////////////////////////////////
    private val iRepository: RepositoryInterface =repository
//////////////////////////////////////////////////////////////

    init {
        getFromFFavorite()
    }


    fun insertToFavorite(favModel: MapModel) {
        viewModelScope.launch(Dispatchers.IO) {
            iRepository.insertToFavorite(favModel)
        }
    }



  fun getFromFFavorite() =
        viewModelScope.launch {
           // withContext(Dispatchers.IO) {
                iRepository.getAllFavoriteFromRoom().catch { e ->
                    _data.value = ApiStateFav.Failure(e)
                }.collect { data ->
                    _data.value = ApiStateFav.Success(data)
                }

            //}
        }



    fun deleteFromFav(favModel: MapModel) {
        viewModelScope.launch(Dispatchers.IO) {
            iRepository.deleteFromFavorite(favModel)
        }
    }


}