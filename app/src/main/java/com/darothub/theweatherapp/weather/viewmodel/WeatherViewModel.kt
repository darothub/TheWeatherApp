package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.CurrentWeatherAndForecast
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.UIState
import com.darothub.theweatherapp.com.darothub.theweatherapp.weather.repository.WeatherRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

class WeatherViewModel(
    val weatherRepositoryImpl: WeatherRepositoryImpl
) : ViewModel() {
    private val _currentWeatherFlow = MutableStateFlow<UIState>(UIState.Nothing)
    val currentWeatherFlow = _currentWeatherFlow.asStateFlow()

    fun getCurrentWeather(key:String, q: String, days:Int) {
        _currentWeatherFlow.value = UIState.Loading
        val response = weatherRepositoryImpl.getCurrentWeather(q)
        response.map {
           if (it.isEmpty()) {
              try {
                  weatherRepositoryImpl.getCurrentWeatherFromApi(key, q, days)
              } catch (e:Exception){
                  _currentWeatherFlow.value = UIState.Error(e)
                  Log.e("ViewModel", e.localizedMessage)
              }
           }
            _currentWeatherFlow.value = UIState.Success(it)

        }.launchIn(viewModelScope)
    }
}