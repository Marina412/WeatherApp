package com.example.weatherapp.Fav.View


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Fav.ViewModel.FavoriteViewModel
import com.example.weatherapp.Fav.ViewModel.FavoriteViewModelFactory
import com.example.weatherapp.LocalDatabase.ConcreteLocalSource
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.Model.Repository
import com.example.weatherapp.Networking.APIClient
import com.example.weatherapp.R
import com.example.weatherapp.Utils.ApiStateFav
import com.example.weatherapp.databinding.FragmentFavoriteBinding




private const val TAG = "FavoriteFragment"
class FavoriteFragment : Fragment(), FavOnClickListener{

    lateinit var binding: FragmentFavoriteBinding

    lateinit var viewModel: FavoriteViewModel
    lateinit var fViewModelFactory: FavoriteViewModelFactory
    lateinit var repository: Repository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding= FragmentFavoriteBinding.inflate(inflater,container,false)

        val localSource = ConcreteLocalSource(requireContext())
        val remoteSource= APIClient.getInstane()
        repository =  Repository.getInstance(localSource,remoteSource)
        fViewModelFactory = FavoriteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, fViewModelFactory).get(FavoriteViewModel::class.java)





        lifecycleScope.launchWhenCreated {
            viewModel.data.collect{
                when(it){
                    is ApiStateFav.Success ->{
                        successFavData(it.data)
                    }
                    is ApiStateFav.Failure -> {
                        Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Log.i(TAG, "onCreateView: else")
                    }
                }
            }
        }





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnAddFavLoc.setOnClickListener {
            findNavController().navigate(R.id.mapFragment)
        }


    }


    fun successFavData(data:List<MapModel>){

        binding.favRecycle.adapter =FavCountriesAdapter(data,this)
        binding.favRecycle.layoutManager = LinearLayoutManager(requireContext())
        binding.favRecycle.apply {

            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = RecyclerView.VERTICAL
            }
        }
    }


    override fun onDeleteClick(favorite: MapModel) {
      showDialog(favorite)
    }

    override fun onFavClick(latLong: String) {
       Toast.makeText(requireContext(),"jdnwqnnjncjdnjn",Toast.LENGTH_SHORT).show()
    }

    private fun showDialog(favorite: MapModel) {
        val alertBuild: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertBuild.setTitle("Delete from  Favorite")
        alertBuild.setMessage("DO you want to delete this location from favorite?")
        alertBuild.setPositiveButton("OK") {
                _, _ ->

            Toast.makeText(requireContext(), "Location $favorite delete to fav", Toast.LENGTH_SHORT).show()

            viewModel.deleteFromFav(favorite )
        }

        alertBuild.setNegativeButton("Cancel!") { _, _ ->
            Toast.makeText(requireContext(),"Delete Location canceld",Toast.LENGTH_SHORT).show()
        }

        val alert = alertBuild.create()
        alert.show()


    }




}