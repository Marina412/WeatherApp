package com.example.weatherapp.Home.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.Model.Repository

class HomeViewModelFactory(val repository: Repository) :
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(repository) as T
        }
        else{
            throw java.lang.IllegalArgumentException("ViewModel not founded")
        }


    }
}