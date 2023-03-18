package com.example.weatherapp.Alerts.View

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
import com.example.weatherapp.Alerts.ViewModel.AlertsViewModel
import com.example.weatherapp.Alerts.ViewModel.AlertsViewModelFactory
import com.example.weatherapp.Data.Repository
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.R
import com.example.weatherapp.Utils.ApiStateAlerts
import com.example.weatherapp.Utils.CustomConfermation.AlertButtonResult
import com.example.weatherapp.Utils.CustomConfermation.UtilsDialog
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.FragmentAlertsBinding


class AlertsFragment : Fragment(),AlertsOnClickLisener {

    lateinit var alertButtonResult: AlertButtonResult



    lateinit var viewModel: AlertsViewModel
    lateinit var AViewModelFactory: AlertsViewModelFactory
    lateinit var repository: Repository


lateinit var binding: FragmentAlertsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAlertsBinding.inflate(inflater,container,false)


        repository =  Repository.getInstance(requireActivity().application)

        AViewModelFactory = AlertsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, AViewModelFactory).get(AlertsViewModel::class.java)



        //viewModel.getFromAlerts()
        lifecycleScope.launchWhenCreated {
            viewModel.data.collect{
                when(it){
                    is ApiStateAlerts.Success ->{
                        successAlertsData(it.dataState)
                    }
                    is ApiStateAlerts.Failure -> {
                        Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Log.i("TAGAlertsF", "onCreateView: else")
                    }
                }
            }
        }




        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddNewAlert.setOnClickListener {


            if(UtilsFunction.isOnline(requireContext()))
            {
                AddNewAlertFragment().show(requireActivity().supportFragmentManager,"AlertDialogFragment")
            }

            else
            {
                Toast.makeText(requireContext(),R.string.offline_mode,Toast.LENGTH_LONG).show()
            }





        }
    }




    private fun successAlertsData(data:List<RoomAlertsModel>){

        binding.alertsRecycle.adapter = AlertsAdapter(data,this)
        binding.alertsRecycle.layoutManager = LinearLayoutManager(requireContext())
        binding.alertsRecycle.apply {

            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = RecyclerView.VERTICAL
            }
        }
    }

    override fun onDeleteClick(alertsModel: RoomAlertsModel) {


        alertButtonResult= object : AlertButtonResult {
            override fun IfOk() {
                Toast.makeText(requireContext(), "${getString(R.string.message_Deletion_Done)}  $alertsModel ", Toast.LENGTH_LONG).show()
                viewModel.deleteFromAlerts(alertsModel)
            }

            override fun IfCancel() {
                Toast.makeText(requireContext(),getString(R.string.message_Deletion_canceld), Toast.LENGTH_SHORT).show()
            }

        }

        UtilsDialog.showDialog(getString(R.string.title_delete_alerts_loc),
            getString(R.string.message_delete_alerts_loc),
            alertButtonResult,requireContext())
    }


}

