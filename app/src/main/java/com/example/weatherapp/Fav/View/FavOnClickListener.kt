package com.example.weatherapp.Fav.View

import com.example.weatherapp.Model.MapModel

interface FavOnClickListener {
    fun onDeleteClick(favorite: MapModel)
    fun onFavClick(latLong:String)
}