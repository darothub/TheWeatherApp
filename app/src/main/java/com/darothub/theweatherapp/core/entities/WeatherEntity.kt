package com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities

import androidx.room.*
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Location

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "weather_id")
    val id: Long = 0,
    @Embedded
    val current: CurrentWeatherEntity,
    @Embedded
    val location: Location,
)

data class CurrentWeatherWithForeCast(
    @Embedded
    val weatherEntity: WeatherEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "location_name"
    )
    val forecastDayEntity: List<ForecastDayEntity>
)