package com.example.weatherapp.Data.Source.LocalDatabase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Model.Root
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class WeatherDAOTest {

    private lateinit var database: WeatherDataBase
    private lateinit var weatherDAO: WeatherDAO

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).allowMainThreadQueries().build()
        weatherDAO=database.weatherDAO()
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun insertLastResponse_insertItem_returnsTrue() {

        // GIVEN - insert a  item
        val data1 = RoomWeatherModel(
            id = 1,
            weather = Root(
                lat = 1.1
            )
        )
        val data2 = RoomWeatherModel(
            id = 2,
            weather = Root(
                lat = 2.2
            )
        )
        val data3 = RoomWeatherModel(
            id = 3,
            weather = Root(
                lat = 3.3
            )
        )
        val data4 = RoomWeatherModel(
            id = 4,
            weather = Root(
                lat = 4.4
            )
        )



        weatherDAO.insertLastResponse(data1)
        weatherDAO.insertLastResponse(data2)
        weatherDAO.insertLastResponse(data3)
        weatherDAO.insertLastResponse(data4)
        // WHEN - Get the task  from the database
        val result = weatherDAO.getLastResponse()
        // THEN - The result  data contains the expected values

        MatcherAssert.assertThat(result.id, `is`(0))
        MatcherAssert.assertThat(result.weather.lat, `is`(data4.weather.lat))
    }



    @Test
    fun getLastResponse()  {

        // GIVEN - insert a  item
        val data1 = RoomWeatherModel(
            id = 0,
            weather = Root()
        )

        val data2 = RoomWeatherModel(
            id = 1,
            weather = Root()
        )
        weatherDAO.insertLastResponse(data1)
        weatherDAO.insertLastResponse(data2)

        // WHEN - Get the task  from the database
        val result = weatherDAO.getLastResponse()
        // THEN - The result  data contains the expected values

        MatcherAssert.assertThat(result.id, `is`(0))
    }
}