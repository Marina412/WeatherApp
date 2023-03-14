package com.example.weatherapp.Data.Source.RemoteNetworking

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class APIInterfaceTest {
    private var APIInterface: APIInterface?=null

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
         APIInterface= APIClient.getInstane()
    }



    @ExperimentalCoroutinesApi
    @Test
    fun getWeatherDataCurrentStander_callAPI_returnTrue() = runBlocking {
        //GIVEN init variable
        var latitude =33.44
        var longitude = -94.04

        //WHEN call the api
        val root=APIInterface?.getWeatherDataCurrentStander(latitude , longitude)

        //THEN The retrofit calls the api for the expected result  data

        print(root)
        MatcherAssert.assertThat(root, IsNull.notNullValue())

    }
}