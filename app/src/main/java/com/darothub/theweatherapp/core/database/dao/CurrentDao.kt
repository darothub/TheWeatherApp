package com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.CurrentWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentDao : BaseDao<CurrentWeatherEntity>{
//    @Query("SELECT * FROM currentweatherentity")
//    fun getCurrentWeather(): Flow<List<CurrentWeatherEntity>>
}