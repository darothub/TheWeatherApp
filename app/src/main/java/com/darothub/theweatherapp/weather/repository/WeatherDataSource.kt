package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.repository

import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.WeatherDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.WeatherEntity

class WeatherDataSource(
    private val weatherDao: WeatherDao,
) {
    suspend fun insertCurrent(currentWeather: WeatherEntity){
        weatherDao.insert(currentWeather)
    }
    fun getCurrentWeather(lat: String, long: String) = weatherDao.getCurrentWeather(lat, long)

}