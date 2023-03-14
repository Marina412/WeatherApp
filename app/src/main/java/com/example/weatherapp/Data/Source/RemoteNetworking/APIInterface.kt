package com.example.weatherapp.Data.Source.RemoteNetworking



import com.example.weatherapp.Model.Root
import com.example.weatherapp.Utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("onecall")
    suspend fun getWeatherDataCurrentStander(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = Constants.EXCLUDE,
        @Query("appid") apiKey: String = Constants.WEATHER_API_KEY,
        @Query("lang") lang: String=Constants.ENGLISH

    ): Root

}