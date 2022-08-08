package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.UIState
import com.darothub.theweatherapp.com.darothub.theweatherapp.weather.repository.WeatherRepositoryImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.*
import kotlin.coroutines.resume

class WeatherViewModel(
    private val weatherRepositoryImpl: WeatherRepositoryImpl
) : ViewModel() {
    private val _currentWeatherFlow = MutableStateFlow<UIState>(UIState.Nothing)
    val currentWeatherFlow = _currentWeatherFlow.asStateFlow()
    private val _locationFlow = MutableStateFlow<UIState>(UIState.Nothing)
    val locationFlow = _locationFlow.asStateFlow()

    suspend fun getCurrentWeather(key:String, lat: String, long: String, days:Int) {
        _currentWeatherFlow.value = UIState.Loading
        val currentWeatherList = weatherRepositoryImpl.getCurrentWeather(lat, long)

        currentWeatherList.map{ current->
            Log.d("ViewModel", "$current")
            if (current.isEmpty()) {
                try {
                    Log.d("ViewModel", "Call")
                    weatherRepositoryImpl.fetchRemoteCurrentWeather(key, "$lat,$long", days)
                } catch (e:Exception){
                    Log.e("ViewModel", e.localizedMessage)
                    _currentWeatherFlow.value = UIState.Error(e)
                }
            }

            _currentWeatherFlow.value = UIState.Success(current)

        }.stateIn(viewModelScope)

    }
    @SuppressLint("MissingPermission")
    fun getLocation(mfusedLocationProviderClient: FusedLocationProviderClient) {
        _locationFlow.value = UIState.Loading
        val priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY
        val cancellationTokenSource = CancellationTokenSource().token
        mfusedLocationProviderClient.getCurrentLocation(priority, cancellationTokenSource).addOnSuccessListener { location ->
            Log.d("Main", "lat $location")
            if (location != null) {
                _locationFlow.value = UIState.Success(location)
            }
        }
    }

}