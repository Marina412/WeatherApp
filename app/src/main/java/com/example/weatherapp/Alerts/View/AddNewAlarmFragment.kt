package com.example.weatherapp.Alerts.View

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherapp.Alerts.ViewModel.AlertsViewModel
import com.example.weatherapp.Alerts.ViewModel.AlertsViewModelFactory
import com.example.weatherapp.Data.Repository
import com.example.weatherapp.MainActivity
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.R
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.CustomConfermation.AlertButtonResult
import com.example.weatherapp.Utils.CustomConfermation.UtilsDialog
import com.example.weatherapp.databinding.FragmentAddNewAlertBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AddNewAlertFragment : DialogFragment() {

    lateinit var binding: FragmentAddNewAlertBinding


    private var startYear: Int = 0
    private var startMonth: Int = 0
    private var startDay: Int = 0
    private var endYear: Int = 0
    private var endMonth: Int = 0
    private var endDay: Int = 0
    private var hour: Int = 0
    private var minutes: Int = 0
    private lateinit var startDate: String
    private lateinit var endDate: String
    private  var endDateWorker: Long=0

    private lateinit var alartTime: String
    lateinit var viewModel: AlertsViewModel
    lateinit var AViewModelFactory: AlertsViewModelFactory
    lateinit var repository: Repository

    private var lat: Double = 0.0
    private var long: Double = 0.0
    private var time: Long = 0

    private lateinit var alertButtonResult: AlertButtonResult


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNewAlertBinding.inflate(inflater, container, false)


        val sharedPreferences =
            requireActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)

        repository = Repository.getInstance(requireActivity().application)

        AViewModelFactory = AlertsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, AViewModelFactory).get(AlertsViewModel::class.java)

        lat = sharedPreferences.getString("latitude", "")!!.toDouble()
        long = sharedPreferences.getString("longitude", "")!!.toDouble()


        binding.tvAlertStartDate.setOnClickListener {
            showDatePikerDialogStartDate()
        }
        binding.tvAlertEndDate.setOnClickListener {
            showDatePikerDialogEndDate()
        }
        binding.tvAlertTime.setOnClickListener {
            popTimePiker()
        }
        binding.alertOkBtn.setOnClickListener {
            checkOverlayPermission()
            val roomAlertsModel = RoomAlertsModel(
                lon = long,
                lat = lat,
                //startDate =startDate,
                endDate = endDate,
                time = alartTime,
                hour = hour,
                minute = minutes,
                startDay = startDay,
                startMonth = startMonth,
                startYear = startYear,
                //started = true
            )
            viewModel.insertToAlerts(roomAlertsModel)
            //schedule(requireContext(),roomAlertsModel)
            setAlarmWorker()

            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_LONG).show()
            dismiss()
        }
        binding.alertCancelBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }


    private fun showDatePikerDialogStartDate() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            OnDateSetListener { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                this.onDateStartSet(
                    view,
                    year,
                    month,
                    dayOfMonth
                )
            },
            Calendar.getInstance()[Calendar.YEAR],
            Calendar.getInstance()[Calendar.MONTH],
            Calendar.getInstance()[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    fun onDateStartSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        startYear = year
        startMonth = month + 1
        startDay = dayOfMonth
        startDate = "" + dayOfMonth + '/' + (month + 1) + '/' + year
        binding.tvAlertStartDate.text = startDate
    }

    private fun showDatePikerDialogEndDate() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            OnDateSetListener { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                this.onDateEndSet(
                    view,
                    year,
                    month,
                    dayOfMonth
                )
            },
            Calendar.getInstance()[Calendar.YEAR],
            Calendar.getInstance()[Calendar.MONTH],
            Calendar.getInstance()[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    fun onDateEndSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        endYear = year
        endMonth = month
        endDay = dayOfMonth
        val endTimeCalender:Calendar=Calendar.getInstance()
            endTimeCalender.set(year,month,dayOfMonth)
        endDateWorker=endTimeCalender.timeInMillis
        endDate = "" + dayOfMonth + '/' + (month + 1) + '/' + year
        binding.tvAlertEndDate.text = endDate
    }


    private fun popTimePiker() {
        val cal = Calendar.getInstance()
        val onTimeSetListener =
            OnTimeSetListener { view, selectedHour, selectedMinute ->
                cal[Calendar.HOUR_OF_DAY] = selectedHour
                cal[Calendar.MINUTE] = selectedMinute
                time=(TimeUnit.MINUTES.toSeconds(selectedMinute.toLong())+TimeUnit.HOURS.toSeconds(selectedHour.toLong()))
                time=time.minus(3600L*2)
                hour = cal[Calendar.HOUR_OF_DAY]
                minutes = cal[Calendar.MINUTE]
                val simpleDateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
                alartTime = simpleDateFormat.format(cal.time)
                binding.tvAlertTime.text = alartTime
            }
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            onTimeSetListener,
            cal[Calendar.HOUR],
            cal[Calendar.MINUTE],
            false
        )
        timePickerDialog.setTitle("Select Time")
        timePickerDialog.show()
    }

/*

    private fun setAlarmWorker() {
        val worker = PeriodicWorkRequestBuilder<AlarmWorkManger>(
            1,
            TimeUnit.DAYS
        ).setInitialDelay(5, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(requireContext()).enqueue(worker)
    }
*/


    private fun setAlarmWorker() {
        val date = Calendar.getInstance().timeInMillis.div(1000)
        val inQueue = ((date - time) / 60 / 60 / 60 / 60) - 115
        val data = Data.Builder()
        data.putLong("endDate", endDateWorker)
        val worker = PeriodicWorkRequestBuilder<AlarmWorkManger>(
            1,
            TimeUnit.DAYS
        )
            .setInitialDelay(inQueue, TimeUnit.SECONDS)
            .addTag("$inQueue")
            .setInputData(data.build())
            .build()
        WorkManager.getInstance(requireContext()).enqueue(worker)

    }


    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(requireContext())) {

                alertButtonResult = object : AlertButtonResult {
                    override fun IfOk() {
                        var myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                        startActivity(myIntent)
                        dialog?.dismiss()
                    }

                    override fun IfCancel() {
                        dialog?.dismiss()
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                    }
                }
                UtilsDialog.showDialog(
                    getString(R.string.dailog_permission_title),
                    getString(R.string.dailog_permission_message),
                    alertButtonResult, requireContext()
                )


            }
        }
    }
}


