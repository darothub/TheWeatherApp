package com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao

import androidx.room.Dao
import androidx.room.MapInfo
import androidx.room.Query
import androidx.room.Transaction
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.CurrentWeatherEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.ForecastDayEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.ForecastDayWithHours
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.HourEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.ForecastDayResponse
import kotlinx.coroutines.flow.Flow
typealias CurrentWeatherAndForecast = List<Map<CurrentWeatherEntity, List<ForecastDayEntity>>>
@Dao
interface ForecastDayDao: BaseDao<ForecastDayEntity> {
    @Transaction
    @Query("SELECT * FROM HourEntity JOIN ForecastDayEntity ON dateEpoch > timeEpoch WHERE location_name =:q")
    fun getForecast(q: String): Flow<List<ForecastDayResponse>>
    @Transaction
    @Query("SELECT * FROM forecastdayentity")
    fun getForecastWithHours(): Flow<List<ForecastDayWithHours>>
}