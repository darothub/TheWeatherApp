package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darothub.theweatherapp.com.darothub.theweatherapp.weather.repository.WeatherRepositoryImpl

class WeatherViewModelFactory(
    private val repositoryImpl: WeatherRepositoryImpl
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(repositoryImpl) as T
    }
}