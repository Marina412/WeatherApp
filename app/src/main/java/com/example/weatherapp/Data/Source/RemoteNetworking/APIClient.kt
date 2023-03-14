package com.example.weatherapp.Data.Source.RemoteNetworking

import android.annotation.SuppressLint
import com.example.weatherapp.Utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    @SuppressLint("SuspiciousIndentation")
    fun getInstane(): APIInterface {

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

        return retrofitInstnce.create(APIInterface::class.java)

}


    }