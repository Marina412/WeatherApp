package com.example.weatherapp.Data.Source.LocalDatabase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherapp.Model.MapModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavDAOTest {

    private lateinit var database: WeatherDataBase
    private lateinit var favDAO: FavDAO

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).allowMainThreadQueries().build()
        favDAO=database.favDAO()
    }

    @After
    fun closeDb() = database.close()



    @Test
    fun insertToFavorite_insertItems_returnsTrue() = runBlocking {
        // GIVEN - insert a  item
        val data1 = MapModel(
            latLong = "121.034.0",
           countryName =  "marina",
            adminArea = "atef",
            latitude = 121.0,
            longitude = 34.0 )

        val data2 = MapModel(
            latLong = "122.034.0",
           countryName =  "marina",
            adminArea = "atef",
            latitude = 122.0,
            longitude = 34.0 )

        val data3 = MapModel(
            latLong = "123.034.0",
           countryName =  "marina",
            adminArea = "atef",
            latitude = 123.0,
            longitude = 34.0 )

        favDAO.insertToFavorite(data1)
        favDAO.insertToFavorite(data2)
        favDAO.insertToFavorite(data3)



        // WHEN - Get the task  from the database
        val result = favDAO.getFromFavorite().first()

        // THEN - The result  data contains the expected values

        MatcherAssert.assertThat(result.get(0).latLong , `is`(data1.latLong))
        MatcherAssert.assertThat(result.get(0).latitude , `is`(data1.latitude))
        MatcherAssert.assertThat(result.get(1).latLong , `is`(data2.latLong))
        MatcherAssert.assertThat(result.get(1).latitude , `is`(data2.latitude))
        MatcherAssert.assertThat(result.get(2).latLong , `is`(data3.latLong))
        MatcherAssert.assertThat(result.get(2).latitude , `is`(data3.latitude))
    }

    @Test
    fun getFromFavorite_insertItem_returnsTrue() = runBlocking {


        // GIVEN - insert a  item
        val data1 = MapModel(
            latLong = "121.034.0",
            countryName =  "marina",
            adminArea = "atef",
            latitude = 121.0,
            longitude = 34.0 )

        val data2 = MapModel(
            latLong = "122.034.0",
            countryName =  "marina",
            adminArea = "atef",
            latitude = 122.0,
            longitude = 34.0 )

        val data3 = MapModel(
            latLong = "123.034.0",
            countryName =  "marina",
            adminArea = "atef",
            latitude = 123.0,
            longitude = 34.0 )

        favDAO.insertToFavorite(data1)
        favDAO.insertToFavorite(data2)
        favDAO.insertToFavorite(data3)



        // WHEN - Get the task  from the database
        val result = favDAO.getFromFavorite().first()

        // THEN - The result  data size 3

        MatcherAssert.assertThat(result.size ,`is`(3))

    }

    @Test
    fun deleteFromFavorite_deleteItem_returnsZero() = runBlocking {


        // GIVEN - insert a  item
        val data1 = MapModel(
            latLong = "121.034.0",
            countryName =  "marina",
            adminArea = "atef",
            latitude = 121.0,
            longitude = 34.0 )

        favDAO.insertToFavorite(data1)
        favDAO.deleteFromFavorite(data1)



        // WHEN - Get the task  from the database
        val result = favDAO.getFromFavorite().first()

        // THEN - The result  data size equl 0

        MatcherAssert.assertThat(result.size ,`is`(0))

    }
}