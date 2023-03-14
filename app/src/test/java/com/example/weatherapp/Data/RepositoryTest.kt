package com.example.weatherapp.Data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.MainCoroutineRule
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Model.Root
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryTest {

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
    private var weatherResponse: Root =Root(lat = 5.5,lon=5.5)


    private lateinit var  remoteDataSource : FakeDataSource
    private lateinit var localDataSource: FakeDataSource
    private lateinit var repository: Repository



    @Before
    fun createRepository() {
        remoteDataSource = FakeDataSource(favList, alarmList, roomResponse, weatherResponse)
        localDataSource = FakeDataSource(favList, alarmList, roomResponse, weatherResponse)
        // Get a reference to the class under test
        repository = Repository(
            remoteDataSource, localDataSource//, ApplicationProvider.getApplicationContext() //Dispatchers.Main
        )
    }

    @Test
    fun getAllResponseFromAPI_requestsAllResponseFromRemoteDataSource()  = mainCoroutineRule.runBlockingTest {
            // When  requeste data from the remote source
            val result = repository.getAllResponseFromAPI(lat = 5.5,lon=5.5, lang = "en").first()


            // Then response are loaded from the remote source
           assertThat(result, `is`(weatherResponse))
        }


    @Test
    fun getCurrentWeatherForWorker_requestsAllResponseFromRemoteDataSource()  = mainCoroutineRule.runBlockingTest {
        // When  requeste data from the remote source
        val result = repository.getCurrentWeatherForWorker(lat = 5.5,lon=5.5)


        // Then response are loaded from the remote source
        assertThat(result, `is`(weatherResponse))
    }

    @Test
    fun insertLastResponse_insertLastResponse_returnTrue() = mainCoroutineRule.runBlockingTest  {
        // Given inserte item
        repository.insertLastResponse(roomResponse)

        // WHEN - Get the task  from the database
        val result = localDataSource.getLastResponseFromRoom()

        //Then check if item inset
        assertThat(result.weather.lat , `is`(dataWeatherModel1.weather.lat))

    }


    @Test
    fun getLastResponseFromDB_insertItem_returnsTrue()   = mainCoroutineRule.runBlockingTest {
        // Given inserte item
        repository.insertLastResponse(roomResponse2)

        // When  requeste data from the local source
        val result = repository.getLastResponseFromDB().first()


        // Then response are loaded from the local source same as last inserted
        assertThat(result.weather.lat, `is`(dataWeatherModel2.weather.lat))
    }
   @Test
    fun insertToFavorite_insertItem_returnTrue() = mainCoroutineRule.runBlockingTest  {
        // When inserte item
        repository.insertToFavorite(dataMapModel3)
        //Then check if item inset
        assertThat(favList[2].latitude,`is`(dataMapModel3.latitude))
    }

    @Test
    fun deleteFromFavorite_deleteItem_returnTure() = mainCoroutineRule.runBlockingTest  {
        // When inserte item
        repository.deleteFromFavorite(dataMapModel3)
        //Then check if item inset
        assertThat(favList.size,`is`(2))
    }


    @Test
    fun getAllFavoriteFromRoom_requestsAlldataFromLocalDataSource_returnTrue() = mainCoroutineRule.runBlockingTest {
        // When items are requested from   repository
        val items = repository.getAllFavoriteFromRoom().first()

        // Then items are loaded from the local data source
        assertThat(items.size, IsEqual(favList.size))
    }

    @Test
    fun insertToAlerts_insertItem_returnTrue() = mainCoroutineRule.runBlockingTest  {
        // When inserte item
        repository.insertToAlerts(dataAlertsModel3)
        //Then check if item inset
        assertThat(alarmList[2].lon,`is`(dataAlertsModel3.lon))
    }

    @Test
    fun deleteFromAlerts_deleteItem_returnTure() = mainCoroutineRule.runBlockingTest  {
        // When inserte item
        repository.deleteFromAlerts(dataAlertsModel3)
        //Then check if item inset
        assertThat(alarmList.size,`is`(2))
    }

    @Test
    fun getFromAlerts_requestsAlldataFromLocalDataSource_returnTrue() = mainCoroutineRule.runBlockingTest {
        // When items are requested from   repository
        val items = repository.getFromAlerts().first()

        // Then items are loaded from the local data source
        assertThat(items.size, IsEqual(alarmList.size))
    }




}