package com.example.weatherapp.Networking



import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.models.Root
import retrofit2.http.GET
import retrofit2.http.Query

interface RemotSource {
//todo delet later
/*

    @GET("onecall")
    suspend fun getWeatherDataCurrentStandertest(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = Constants.EXCLUDE,
        @Query("appid") apiKey: String = Constants.WEATHER_API_KEY

    ): Response<RoomWeatherModel>

*/

    @GET("onecall")
    suspend fun getWeatherDataCurrentStander(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = Constants.EXCLUDE,
        @Query("appid") apiKey: String = Constants.WEATHER_API_KEY

    ): Root


    @GET("onecall")
    suspend fun getWeatherDataCurren(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = Constants.EXCLUDE,
        @Query("appid") apiKey: String = Constants.WEATHER_API_KEY,
        @Query("units") units: String,
    ): Root


    @GET("onecall")
    suspend fun getWeatherDataCurrentLang(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String = Constants.WEATHER_API_KEY,
        @Query("units") units: String,
        @Query("lang") lang: String

    ): Root


}