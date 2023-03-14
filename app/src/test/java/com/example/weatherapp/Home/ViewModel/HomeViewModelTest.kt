package com.example.weatherapp.Home.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.Data.FakeRepository
import com.example.weatherapp.Data.RepositoryInterface
import com.example.weatherapp.MainCoroutineRule
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Model.Root
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()




    val dataAlertsModel1 = RoomAlertsModel(
        id = 1, lon = -94.041, lat = 33.441,
        endDate = "11/11/2023", time = "11:15 AM", hour = 11,
        minute = 15, startDay = 11, startMonth = 11, startYear = 2023
    )

    val dataAlertsModel2 = RoomAlertsModel(
        id = 2, lon = -94.042, lat = 33.442,
        endDate = "11/11/2023", time = "11:15 AM", hour = 11,
        minute = 15, startDay = 11, startMonth = 11, startYear = 2023
    )
    val dataAlertsModel3 = RoomAlertsModel(
        id = 3, lon = -94.043, lat = 33.443,
        endDate = "11/11/2023", time = "11:15 AM", hour = 11, minute = 15,
        startDay = 11, startMonth = 11, startYear = 2023
    )

    val dataWeatherModel1 = RoomWeatherModel(id = 1, weather = Root(lat = 1.1 ,lon=1.1))
    val dataWeatherModel2 = RoomWeatherModel(id = 2, weather = Root(lat = 2.2,lon=2.2))
    val dataMapModel1 = MapModel(latLong = "121.034.0", countryName =  "marina", adminArea = "atef", latitude = 121.0, longitude = 34.0 )

    val dataMapModel2 = MapModel(latLong = "122.034.0", countryName =  "marina", adminArea = "atef", latitude = 122.0, longitude = 34.0 )

    val dataMapModel3 = MapModel(latLong = "123.034.0", countryName =  "marina", adminArea = "atef", latitude = 123.0, longitude = 34.0 )


    private var favList:MutableList<MapModel> = mutableListOf<MapModel>(
        dataMapModel1,dataMapModel2
    )
    private var alarmList:MutableList<RoomAlertsModel> = mutableListOf<RoomAlertsModel>(
        dataAlertsModel1,dataAlertsModel2
    )
    private var roomResponse: RoomWeatherModel = dataWeatherModel1
    private var roomResponse2: RoomWeatherModel = dataWeatherModel2
    private var weatherResponse: Root = Root(lat = 5.5,lon=5.5)


    private lateinit var repository: RepositoryInterface

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        repository=FakeRepository(favList, alarmList,roomResponse,weatherResponse)

        viewModel= HomeViewModel(repository)
    }



    @Test
    fun getAllWeatherStander() {
    }

    @Test
    fun insertLastResponse_insertItem_returnTrue() = mainCoroutineRule.runBlockingTest  {
    }

    @Test
    fun getLastResponseFromRoom() {
    }


}