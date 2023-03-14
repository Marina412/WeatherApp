package com.example.weatherapp.Fav.View


import android.content.Context
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
import com.example.weatherapp.Data.Repository
import com.example.weatherapp.Fav.ViewModel.FavoriteViewModel
import com.example.weatherapp.Fav.ViewModel.FavoriteViewModelFactory
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.R
import com.example.weatherapp.Utils.ApiStateFav
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.CustomConfermation.AlertButtonResult
import com.example.weatherapp.Utils.CustomConfermation.UtilsDialog
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.FragmentFavoriteBinding


private const val TAG = "FavoriteFragment"
class FavoriteFragment : Fragment(), FavOnClickListener{

    lateinit var binding: FragmentFavoriteBinding

    lateinit var viewModel: FavoriteViewModel
    lateinit var fViewModelFactory: FavoriteViewModelFactory
    lateinit var repository: Repository

    lateinit var alertButtonResult: AlertButtonResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding= FragmentFavoriteBinding.inflate(inflater,container,false)


       val sharedPreferences = requireActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)

        repository =  Repository.getInstance(requireActivity().application)

        fViewModelFactory = FavoriteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, fViewModelFactory).get(FavoriteViewModel::class.java)





        lifecycleScope.launchWhenCreated {
            viewModel.data.collect{
                when(it){
                    is ApiStateFav.Success ->{
                        successFavData(it.dataState)
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


            if(UtilsFunction.isOnline(requireContext()))
            {
                findNavController().navigate(R.id.mapFragment)
            }

            else
            {
                Toast.makeText(requireContext(),R.string.offline_mode,Toast.LENGTH_LONG).show()
            }


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


        alertButtonResult= object : AlertButtonResult {
            override fun IfOk() {
                viewModel.deleteFromFav(favorite )
               Toast.makeText(requireContext(), "${getString(R.string.message_Deletion_Done)}  $favorite ", Toast.LENGTH_LONG).show()

            }

            override fun IfCancel() {
                Toast.makeText(requireContext(),getString(R.string.message_Deletion_canceld), Toast.LENGTH_SHORT).show()
            }

        }


        UtilsDialog.showDialog(getString(R.string.title_delete_fav_loc),
            getString(R.string.message_delete_fav_loc),
            alertButtonResult,requireContext())
    }

    override fun onFavClick(favorite: MapModel) {
        val bundle=Bundle()
        bundle.putSerializable("favMapModel",favorite)
        findNavController().navigate(R.id.homeFragment,bundle)
       Toast.makeText(requireContext(),"showing weather",Toast.LENGTH_SHORT).show()
    }




}