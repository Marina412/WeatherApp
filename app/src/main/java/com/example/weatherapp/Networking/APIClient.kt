package com.example.weatherapp.Networking

import com.example.weatherapp.Utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    fun getInstane(): RemotSource{

    val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val retrofitInstnce = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .build()

        return retrofitInstnce.create(RemotSource::class.java)

}


    }