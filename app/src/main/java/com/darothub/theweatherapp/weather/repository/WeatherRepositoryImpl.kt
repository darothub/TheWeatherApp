package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.repository

import com.darothub.theweatherapp.com.darothub.theweatherapp.core.api.ApiService
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.WeatherEntity

class WeatherRepositoryImpl(
    private val apiService: ApiService,
    private val dataSource: WeatherDataSource
) {
    suspend fun fetchRemoteCurrentWeather(key:String, q:String, days:Int) {
        val response = apiService.getCurrentWeather(key, q, days)
        insertCurrent(response)
    }
    suspend fun insertCurrent(currentWeatherEntity: WeatherEntity){
        dataSource.insertCurrent(currentWeatherEntity)
    }
    fun getCurrentWeather(lat: String, long: String) = dataSource.getCurrentWeather(lat, long)

}