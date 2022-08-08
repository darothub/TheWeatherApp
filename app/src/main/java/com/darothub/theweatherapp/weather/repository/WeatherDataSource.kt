package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.repository

import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.CurrentDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.ForecastDayDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.HourDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.WeatherDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.CurrentWeatherEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.ForecastDayEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.HourEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.WeatherEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.Mapper
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.ForecastDay
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.WeatherResponse

class WeatherDataSource(
    private val weatherDao: WeatherDao,
) {
//    suspend fun insertForecastDay(response: WeatherResponse): List<ForecastDayEntity> {
//        val entities = forecastMapper.toEntity(response)
//        entities.map {
//            forecastDaydao.insert(it)
//        }
//        return entities
//    }
    suspend fun insertCurrent(currentWeather: WeatherEntity){
        weatherDao.insert(currentWeather)
    }

//    suspend fun insertHour(hourEntity: HourEntity){
//        hourDao.insert(hourEntity)
//    }
    fun getCurrentWeather(lat: String, long: String) = weatherDao.getCurrentWeather(lat, long)
//    fun getCurrentForecast(q:String) = forecastDaydao.getForecast(q)
//    fun getForecastWithHours() = forecastDaydao.getForecastWithHours()
}