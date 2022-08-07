package com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.CurrentWeatherWithForeCast
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.ForecastDayEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.WeatherEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao: BaseDao<WeatherEntity> {
    @Query("SELECT * FROM weatherentity WHERE lat =:lat AND lon =:longitude")
    fun getCurrentWeather(lat: String, longitude: String) : Flow<List<WeatherEntity>>
    @Transaction
    @Query("SELECT * FROM weatherentity WHERE name =:q")
    fun getCurrentWeatherAndForecast(q:String) : Flow<List<CurrentWeatherWithForeCast>>

}