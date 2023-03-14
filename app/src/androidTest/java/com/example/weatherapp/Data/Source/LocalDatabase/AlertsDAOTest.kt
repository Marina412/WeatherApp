package com.example.weatherapp.Data.Source.LocalDatabase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherapp.Model.RoomAlertsModel
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
class AlertsDAOTest {

    private lateinit var database: WeatherDataBase

    private lateinit var alertsDAO: AlertsDAO
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).allowMainThreadQueries().build()
        alertsDAO=database.alertsDAO()
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun insertToAlerts_insertItem_returnsTrue() = runBlocking {
        // GIVEN - insert   item
        val data1 = RoomAlertsModel(
            id = 1,
            lon = -94.041,
            lat = 33.441,
            endDate = "11/11/2023",
            time = "11:15 AM",
            hour = 11,
            minute = 15,
            startDay = 11,
            startMonth = 11,
            startYear = 2023
        )

        val data2 = RoomAlertsModel(
            id = 2,
            lon = -94.042,
            lat = 33.442,
            endDate = "11/11/2023",
            time = "11:15 AM",
            hour = 11,
            minute = 15,
            startDay = 11,
            startMonth = 11,
            startYear = 2023
        )
        val data3 = RoomAlertsModel(
            id = 3,
            lon = -94.043,
            lat = 33.443,
            endDate = "11/11/2023",
            time = "11:15 AM",
            hour = 11,
            minute = 15,
            startDay = 11,
            startMonth = 11,
            startYear = 2023
        )



        alertsDAO.insertToAlerts(data1)
        alertsDAO.insertToAlerts(data2)
        alertsDAO.insertToAlerts(data3)



        // WHEN - Get the items  from the database
        val result = alertsDAO.getFromAlerts().first()

        // THEN - The result  data contains the expected values

        MatcherAssert.assertThat(result.get(0).lat , `is`(data1.lat))
        MatcherAssert.assertThat(result.get(1).lat , `is`(data2.lat))
        MatcherAssert.assertThat(result.get(2).lat , `is`(data3.lat))
        MatcherAssert.assertThat(result.get(0).lon , `is`(data1.lon))
        MatcherAssert.assertThat(result.get(1).lon , `is`(data2.lon))
        MatcherAssert.assertThat(result.get(2).lon , `is`(data3.lon))

    }

    @Test
    fun getFromAlerts_insertItem_returnsTrue() = runBlocking {


        // GIVEN - insert a  item
        val data1 = RoomAlertsModel(
            id = 1,
            lon = -94.041,
            lat = 33.441,
            endDate = "11/11/2023",
            time = "11:15 AM",
            hour = 11,
            minute = 15,
            startDay = 11,
            startMonth = 11,
            startYear = 2023
        )

        val data2 = RoomAlertsModel(
            id = 2,
            lon = -94.042,
            lat = 33.442,
            endDate = "11/11/2023",
            time = "11:15 AM",
            hour = 11,
            minute = 15,
            startDay = 11,
            startMonth = 11,
            startYear = 2023
        )
        val data3 = RoomAlertsModel(
            id = 3,
            lon = -94.043,
            lat = 33.443,
            endDate = "11/11/2023",
            time = "11:15 AM",
            hour = 11,
            minute = 15,
            startDay = 11,
            startMonth = 11,
            startYear = 2023
        )

        alertsDAO.insertToAlerts(data1)
        alertsDAO.insertToAlerts(data2)
        alertsDAO.insertToAlerts(data3)



        // WHEN - Get the items  from the database
        val result = alertsDAO.getFromAlerts().first()

        // THEN - The result  data size 3

      assertThat(result.size ,`is`(3))

    }

    @Test
    fun deleteFromAlerts_deleteItem_returnsZero() = runBlocking {


        // GIVEN - insert a  item
        val data3 = RoomAlertsModel(
            id = 3,
            lon = -94.043,
            lat = 33.443,
            endDate = "11/11/2023",
            time = "11:15 AM",
            hour = 11,
            minute = 15,
            startDay = 11,
            startMonth = 11,
            startYear = 2023
        )

        alertsDAO.insertToAlerts(data3)
        alertsDAO.deleteFromAlerts(data3)



        // WHEN - Get the items  from the database
        val result =alertsDAO.getFromAlerts().first()

        // THEN - The result  data size equl 0

        MatcherAssert.assertThat(result.size ,`is`(0))

    }
}