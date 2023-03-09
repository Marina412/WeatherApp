package com.example.weatherapp.Home.View

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Home.ViewModel.HomeViewModel
import com.example.weatherapp.Home.ViewModel.HomeViewModelFactory
import com.example.weatherapp.LocalDatabase.ConcreteLocalSource
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.Repository
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Networking.APIClient
import com.example.weatherapp.R
import com.example.weatherapp.Utils.ApiStateWeather
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.models.Current
import com.example.weatherapp.models.Daily
import com.example.weatherapp.models.Hourly

private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {
    lateinit var viewModel: HomeViewModel
    lateinit var hViewModelFactory: HomeViewModelFactory
    lateinit var repository: Repository

    lateinit var binding:FragmentHomeBinding

    lateinit var sharedPreferences: SharedPreferences


    lateinit var tempUnit:String
    lateinit var windSpeedUnit:String


    var mapModel=MapModel("test","map","model",0.0,0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding= FragmentHomeBinding.inflate(inflater,container,false)

        //binding.progressBar.visibility = View.GONE

        sharedPreferences = requireActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)

        tempUnit= sharedPreferences.getString("tempUnit","")!!
        windSpeedUnit= sharedPreferences.getString("windUnit","")!!

        val localSource = ConcreteLocalSource(requireContext())
        val remoteSource=APIClient.getInstane()

        repository =  Repository.getInstance(localSource,remoteSource,sharedPreferences)



        hViewModelFactory = HomeViewModelFactory(repository)
        viewModel =ViewModelProvider(this, hViewModelFactory).get(HomeViewModel::class.java)

        if (UtilsFunction.isOnline(requireContext()))
        {

            if(arguments!=null)
            {
                mapModel = arguments?.getSerializable("favMapModel") as  MapModel
                viewModel.getAllWeatherStander(mapModel.latitude,mapModel.longitude)

                binding.homeCurrentLocLabel.text=getString( R.string.favorite)

                Log.i("ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp", "onCreateView: ${mapModel}")
            }
            else {
                binding.homeCurrentLocLabel.text= getString(R.string.home)
                viewModel.getAllWeatherStander(
                    sharedPreferences.getString("latitude", "")!!.toDouble(),
                    sharedPreferences.getString("longitude", "")!!.toDouble()
                )
            }

        }
        else
        {
            binding.homeCurrentLocLabel.text= getString(R.string.home)

            Toast.makeText(requireContext(), R.string.offline_mode, Toast.LENGTH_LONG).show()

            viewModel.getLastResponseFromRoom()
        }



        lifecycleScope.launchWhenCreated {

            viewModel.data.collect{
                when(it){
                    is ApiStateWeather.Success ->{

                        Log.i(TAG, "onCreateView:  ${it.data.timezone}")

                        successLodaingData()
                        successDailyData(it.data.daily,it.data.timezone,tempUnit)
                        successHourlyData(it.data.hourly,it.data.timezone,tempUnit)
                        successCurrentData(it.data.current,it.data.timezone,tempUnit)
                        binding.locationLabel.text=UtilsFunction.getFullAddress(it.data.lat,it.data.lon,requireContext())

                        if(arguments==null) {
                            viewModel.insertLastResponse(RoomWeatherModel(id=1,wether = it.data))
                        }
                    }
                    is ApiStateWeather.Failure -> {

                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Log.i(TAG, "onCreateView: else")
                        lodaingData()


                    }
                }
            }
        }


        return binding.root
    }


   private fun successDailyData(data:List<Daily>,timeZone:String,tempUnit:String){

        binding.recycleDay.adapter =HomeDailyAdapter(data,timeZone,tempUnit)
        binding.recycleDay.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleDay.apply {

            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = RecyclerView.VERTICAL
            }
        }
    }

@RequiresApi(Build.VERSION_CODES.O)
private    fun successCurrentData(data:Current, timeZone: String, tempUnit: String){
        when (tempUnit){
            Constants.FAHRENHEIT  ->binding.currentTemperature.text=
                data.temp.toString()+"°F"

            Constants.CELSIUS->binding.currentTemperature.text=
                UtilsFunction.convertFromKelvinToCelsius(data.temp)+"°C"

            Constants.KELVIN->binding.currentTemperature.text=
                UtilsFunction.convertFromKelvinToFahrenheit(data.temp)+"°K"
            else->binding.currentTemperature.text=
                UtilsFunction.convertFromKelvinToCelsius(data.temp)+"°C"
        }
        binding.wetherCurrentDesc.text=data.weather[0].description
        binding.currentDate.text=UtilsFunction.getCurrentDate(data.dt,timeZone )
        binding.currentTime.text=UtilsFunction.getCurrentTime(data.dt,timeZone )
        binding.currentWeatherIcon.setImageResource(UtilsFunction.getWeatherIcon(data.weather[0].icon))
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.tvHumidity.text=data.humidity.toString()
        binding.tvCloud.text=data.clouds.toString()
        binding.tvPressure.text=data.pressure.toString()
        binding.tvWind.text=data.windDeg.toString()
        binding.tvVisibility.text=data.visibility.toString()

        when (windSpeedUnit){
            Constants.MILESHOUR  ->
                binding.tvWindSpeed.text=
                    UtilsFunction.convertMeterspersecToMilesperhour(data.windSpeed).toString()

            Constants.METERSEC->
                binding.tvWindSpeed.text=
                    data.windSpeed.toString()
            else->
                binding.tvWindSpeed.text=
                    data.windSpeed.toString()
        }
    }

   private fun successHourlyData(data:List<Hourly>,timezone:String,tempUnit:String){

        binding.recycleHourly.adapter =HomeHourlyAdapter(data,timezone, tempUnit)
        binding.recycleHourly.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleHourly.apply {

            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = RecyclerView.HORIZONTAL
            }
        }

    }

   private fun lodaingData(){
        binding.progressBar.visibility=View.VISIBLE

        binding.locationLabel.visibility=View.GONE
        binding.cardDesc.visibility=View.GONE
        binding.houeluForcast.visibility=View.GONE

        binding.recycleHourly.visibility=View.GONE
        binding.recycleDay.visibility=View.GONE
        binding.dailyForecast.visibility=View.GONE
        binding.cardDetails.visibility=View.GONE

    }
    private fun successLodaingData(){
        binding.progressBar.visibility=View.GONE

        binding.locationLabel.visibility=View.VISIBLE
        binding.cardDesc.visibility=View.VISIBLE
        binding.houeluForcast.visibility=View.VISIBLE

        binding.recycleHourly.visibility=View.VISIBLE
        binding.recycleDay.visibility=View.VISIBLE
        binding.dailyForecast.visibility=View.VISIBLE
        binding.cardDetails.visibility=View.VISIBLE

    }

}




/*

if (UtilsFunction.isOnline(requireContext()))
{

    if(arguments!=null)
    {
        mapModel = arguments?.getSerializable("favMapModel") as  MapModel
        viewModel.getAllWeatherStander(mapModel.latitude,mapModel.longitude)
        binding.homeCurrentLocLabel.text=getString( R.string.favorite)

        Log.i("ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp", "onCreateView: ${mapModel}")
    }
    else {
        binding.homeCurrentLocLabel.text= getString(R.string.home)

        viewModel.getAllWeatherStander(
            sharedPreferences.getString("latitude", "")!!.toDouble(),
            sharedPreferences.getString("longitude", "")!!.toDouble()
        )
    }

}
else{

    Toast.makeText(requireContext(),"of line mode ")

}
*/
