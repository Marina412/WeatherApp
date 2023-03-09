package com.example.weatherapp.Home.View

import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.R
import com.example.weatherapp.Utils.ApiStateWeather
import com.example.weatherapp.Utils.UtilsFunction

class test {

/*
    lifecycleScope.launchWhenCreated {
        if (UtilsFunction.isOnline(requireContext())) {
            //binding.homeCurrentLocLabel.text = getString(R.string.home)

            if(arguments!=null)
            {
                mapModel = arguments?.getSerializable("favMapModel") as MapModel
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


//
//                viewModel.getAllWeatherStander(
//                    sharedPreferences.getString("latitude", "")!!.toDouble(),
//                    sharedPreferences.getString("longitude", "")!!.toDouble()
//                )



            viewModel.data.collect {
                when (it) {
                    is ApiStateWeather.Success -> {

                        Log.i(TAG, "onCreateView:  ${it.data.timezone}")

                        successLodaingData()
                        successDailyData(it.data.daily, it.data.timezone, tempUnit)
                        successHourlyData(it.data.hourly, it.data.timezone, tempUnit)
                        successCurrentData(it.data.current)
                        binding.locationLabel.text = UtilsFunction.getFullAddress(
                            it.data.lat,
                            it.data.lon,
                            requireContext()
                        )

                        viewModel.insertLastResponse(RoomWeatherModel(wether = it.data))

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

        else
        {
            binding.homeCurrentLocLabel.text = getString(R.string.home)

            viewModel.getLastResponseFromRoom()
            Toast.makeText(requireContext(), R.string.offline_mode, Toast.LENGTH_LONG).show()
            viewModel.data.collect {
                when (it) {
                    is ApiStateWeather.Success -> {

                        Log.i(TAG, "onCreateView:  ${it.data.timezone}")
                        successLodaingData()
                        successDailyData(it.data.daily, it.data.timezone, tempUnit)
                        successHourlyData(it.data.hourly, it.data.timezone, tempUnit)
                        successCurrentData(it.data.current)
                        binding.locationLabel.text = UtilsFunction.getFullAddress(
                            it.data.lat,
                            it.data.lon,
                            requireContext()
                        )

                        // viewModel.insertLastResponse(RoomWeatherModel(wether = it.data))

                    }
                    is ApiStateWeather.Failure -> {

                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        lodaingData()


                    }
                }
            }



        }


    }*/

}