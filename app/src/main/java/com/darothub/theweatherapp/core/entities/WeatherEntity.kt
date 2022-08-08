package com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities

import androidx.room.*
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "weather_id")
    val id: Long = 0,
    @Embedded
    val current: Current,
    @Embedded
    val location: Location,
    @Embedded
    val forecast: Forecast?
)







