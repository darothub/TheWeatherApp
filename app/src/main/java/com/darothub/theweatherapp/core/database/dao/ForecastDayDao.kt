package com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.CurrentWeatherEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.ForecastDayEntity
import kotlinx.coroutines.flow.Flow
typealias CurrentWeatherAndForecast = List<Map<CurrentWeatherEntity, List<ForecastDayEntity>>>
@Dao
interface ForecastDayDao: BaseDao<ForecastDayEntity> {
//    @Query("SELECT * FROM forecastdayentity")
//    fun getCurrentWeather(q: String): Flow<CurrentWeatherAndForecast>
}