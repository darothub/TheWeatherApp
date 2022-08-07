package com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.ForecastDayEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.HourEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HourDao: BaseDao<HourEntity> {
//    @Query("SELECT * FROM forecastdayentity JOIN HourEntity ON date = forecastday_date")
//    fun getForecastDayAndHour(): Flow<List<Map<ForecastDayEntity, List<HourEntity>>>>
}