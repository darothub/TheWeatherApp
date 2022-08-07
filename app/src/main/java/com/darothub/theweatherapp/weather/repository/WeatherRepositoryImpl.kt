package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.repository

import com.darothub.theweatherapp.com.darothub.theweatherapp.core.api.ApiService
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.HourEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.WeatherResponse

class WeatherRepositoryImpl(
    private val apiService: ApiService,
    private val dataSource: WeatherDataSource
) {
   suspend fun getRemoterWeatherResponse(key:String, q:String, days:Int) {
       val response = apiService.getWeatherForeCast(key, q, days)
       insertCurrent(response)
       dataSource.insertForecastDay(response)
       response.forecast?.forecastday?.map {
           insertHour(it.hour)
       }
   }
    suspend fun getCurrentWeatherFromApi(key:String, q:String, days:Int) {
        val response = apiService.getCurrentWeather(key, q, days)
        insertCurrent(response)
    }
    suspend fun insertCurrent(currentWeatherEntity: WeatherResponse){
        dataSource.insertCurrent(currentWeatherEntity)
    }
    suspend fun insertHour(hourEntity: List<HourEntity>){
       hourEntity.map {
           dataSource.insertHour(it)
       }
    }
    fun getCurrentWeather(q:String) = dataSource.getCurrentWeather(q)

    fun getCurrentAndForecast(q:String) = dataSource.getCurrentAndForecast(q)

//    fun getForecastHours() = dataSource.getForecastHours()
}