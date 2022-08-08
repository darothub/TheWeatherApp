package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.darothub.theweatherapp.Keys
import com.darothub.theweatherapp.R
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.*
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.UIState
import com.darothub.theweatherapp.com.darothub.theweatherapp.main.BaseFragment
import com.darothub.theweatherapp.com.darothub.theweatherapp.main.MainApplication
import com.darothub.theweatherapp.com.darothub.theweatherapp.weather.viewmodel.WeatherViewModel
import com.darothub.theweatherapp.com.darothub.theweatherapp.weather.viewmodel.WeatherViewModelFactory
import com.darothub.theweatherapp.core.utils.*
import com.darothub.theweatherapp.databinding.FragmentCurrentWeatherBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resume


const val DENIED = 0
const val EXPLAINED = 1

class CurrentWeatherFragment : BaseFragment(R.layout.fragment_current_weather), DialogInterface.OnClickListener {
    private val binding by viewBinding(FragmentCurrentWeatherBinding::bind)
    lateinit var permissionHelper: PermissionHelper<Array<String>, Map<String, Boolean>>
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var locationObserver: LocationObserver
    lateinit var alertDialogListener: DialogInterface.OnClickListener
    val viewModel by viewModels<WeatherViewModel> { WeatherViewModelFactory(MainApplication.createWeatherRepository()) }
    lateinit var adapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alertDialogListener = this
        permissionHelper = PermissionHelper(this) { isGranted(it) }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationObserver = LocationObserver(mFusedLocationClient)
        checkLocationPermission()
        collectCurrentWeatherDetails()
        binding.swipeLayout.setOnRefreshListener {
            getWeatherResult()
            binding.swipeLayout.isRefreshing = false
        }
    }

    private fun collectCurrentWeatherDetails() {
        lifecycleScope.launchWhenResumed {
            viewModel.currentWeatherFlow.collect { state ->
                when (state) {
                    is UIState.Loading -> {
                        loading()
                    }
                    is UIState.Success<*> -> {
                        onCurrentWeather(state.data)
                    }
                    is UIState.Error -> {
                        onError(state.exception.message)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) + ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED -> {
                permissionHelper(ActivityResultContracts.RequestMultiplePermissions())
                    .launch(
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
            }
            else -> {
                Log.d("Main", "granted")
                getWeatherResult()
            }
        }
    }

    private fun getWeatherResult() {
        lifecycle.addObserver(locationObserver)
        viewModel.getLocation(mFusedLocationClient)
        lifecycleScope.launchWhenResumed {
            viewModel.locationFlow.collect {uiState->
                when(uiState){
                    is UIState.Loading -> loading()
                    is UIState.Success<*> -> onLocationReady(uiState.data as Location)
                    else -> {}
                }
            }
        }
    }

    override fun onLocationReady(location: Location) {
        super.onLocationReady(location)
        val lat =location.latitude.roundOff()
        val long =location.longitude.roundOff()
        lifecycleScope.launchWhenResumed {
            viewModel.getCurrentWeather(Keys.apiKey(), lat, long, 3)
        }
    }

    private fun isGranted(value: Map<String, Boolean>) {
        val deniedList: List<String> = value.filter { !it.value}
            .map { it.key }
        when {
            deniedList.isNotEmpty() -> {
                val map = deniedList.groupBy { permission ->
                    if (shouldShowRequestPermissionRationale(permission)) DENIED else EXPLAINED
                }
                map[DENIED]?.let { permissionList->
                    // request denied , explain and ask again
                    AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.location_permission))
                        .setMessage(getString(R.string.location_rationale))
                        .setCancelable(false)
                        .setPositiveButton("Ask again"){p0, p1 ->
                            permissionHelper.launch(permissionList.toTypedArray())
                        }.show()
                }
                map[EXPLAINED]?.let {
                    //request denied again, send to settings
                    AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.location_permission))
                        .setMessage(getString(R.string.open_setting_for_location))
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), alertDialogListener)
                        .show()
                }
            }
            else -> {
                Log.d("Main", "Permissions accepted")
                getWeatherResult()
            }
        }
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        val callGPSSettingIntent =
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(callGPSSettingIntent)
    }
    @SuppressLint("MissingPermission")
    @Deprecated("Use onLocationReady")
    suspend fun getUserCurrentLocation(): Location? {
        val priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY
        val cancellationTokenSource = CancellationTokenSource().token
        return suspendCancellableCoroutine { cont ->
            mFusedLocationClient.getCurrentLocation(priority, cancellationTokenSource).addOnSuccessListener { location ->
                Log.d("Main", "lat $location")
                if (location != null) {
                    val locality = getAddressFromLatLng(location)
                    Log.d("Main2", "lat $location")
                    cont.resume(location)
                }
            }
        }
    }



    private fun getAddressFromLatLng(location: Location): String? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val result = try {
            val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addressList != null) {
                val singleAddress = addressList[0]
                val locality = singleAddress.locality.replace(" ", "")
                Log.d("ADDRESS", "$singleAddress $locality")
                singleAddress.locality
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("Address", e.message.toString())
            onError(e.message)
            return null
        }
        return result.toString()
    }

    override fun <T> onCurrentWeather(data: T) {
        super.onSuccess(data)
        val response = data as? List<WeatherEntity>
        if (response?.isNotEmpty() == true){
            val we = response[0]
            val tempInCelsius = we.current.tempC
            val s = convertTempToScientificReading(tempInCelsius ?: 0.0)
            setTopViewData(we, s)
            setUpViewPager(we)
        }
        Log.d("Success", "$response")
    }

    @SuppressLint("SetTextI18n")
    private fun setTopViewData(
        wr: WeatherEntity,
        s: SpannableString
    ) {
        binding.main.apply {
            locationNameTv.text = getString(R.string.location, wr.location.region, wr.location.country)
            val desc = wr.current.condition.text
            temp.text = s
            description.text = desc
            val icon = "https:" + wr.current.condition.icon
            binding.main.weatherImage.load(icon)
            wind.text = "Wind: ${wr.current.windMph}m/s"
            pressure.text = "Pressure: ${wr.current.pressureIn}hPa"
            humidity.text = "Humidity: ${wr.current.humidity}%"
            sunrise.visibility = View.GONE
            val lastUpdated = convertLongToTime(wr.current.lastUpdatedEpoch)
            updateDateTv.text = "Last update: $lastUpdated"
        }
    }
    private fun setUpViewPager(data: WeatherEntity) {
        adapter = ViewPagerAdapter(requireActivity(), 3) { position ->
            val listOfDays = data.forecast?.forecastday
            when (position) {
                0 -> {
                    ForecastFragment.newInstance(listOfDays?.get(0)?.hour)
                }
                1 -> {
                    ForecastFragment.newInstance(listOfDays?.get(1)?.hour)
                }
                else -> {
                    ForecastFragment.newInstance(listOfDays?.get(2)?.hour)
                }
            }
        }

        binding.vp.adapter = adapter
        TabLayoutMediator(binding.mainTabLayout, binding.vp) { tab, position ->
            when (position) {
                0 -> tab.text = "Today"
                1 -> tab.text = "Tomorrow"
                2 -> tab.text = "Later"
            }
        }.attach()
    }
}
