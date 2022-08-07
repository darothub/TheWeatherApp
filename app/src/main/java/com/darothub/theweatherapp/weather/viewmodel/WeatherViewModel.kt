package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.UIState
import com.darothub.theweatherapp.com.darothub.theweatherapp.weather.repository.WeatherRepositoryImpl
import kotlinx.coroutines.flow.*

class WeatherViewModel(
    private val weatherRepositoryImpl: WeatherRepositoryImpl
) : ViewModel() {
    private val _currentWeatherFlow = MutableStateFlow<UIState>(UIState.Nothing)
    val currentWeatherFlow = _currentWeatherFlow.asStateFlow()
    private val _forecastFlow = MutableStateFlow<UIState>(UIState.Nothing)
    val forecastFlow = _forecastFlow.asStateFlow()

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

}