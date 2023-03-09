package com.example.weatherapp.LocalDatabase

import androidx.room.*
import com.example.weatherapp.Model.MapModel
import kotlinx.coroutines.flow.Flow


@Dao
interface FavDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertToFavorite(favModel: MapModel)

    @Query("SELECT * FROM weatherFavorite")
    fun getFromFavorite() : Flow<List<MapModel>>

    @Delete
    fun deleteFromFavorite(favModel:MapModel)



}