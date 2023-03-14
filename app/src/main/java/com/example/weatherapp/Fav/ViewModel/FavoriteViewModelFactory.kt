package com.example.weatherapp.Fav.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.Data.RepositoryInterface

class FavoriteViewModelFactory(val repository: RepositoryInterface) :
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(repository) as T
        }
        else{
            throw java.lang.IllegalArgumentException("ViewModel not founded")
        }


    }
}