package com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities

import androidx.room.*
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Astro
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Condition
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Location
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "weather_id")
    val id: Long = 0,
    @Embedded
    val current: CurrentWeatherEntity,
    @Embedded
    val location: Location,
    @Embedded
    val forecast: Forecast?
)

data class Forecast(
    val forecastday: List<Forecastday>
)

data class Forecastday(
    val date: String,
    val dateEpoch: Long,
    @Embedded
    val astro: Astro,
    @Embedded
    val hour: List<Hour>
)

data class Hour(
    @SerializedName("time_epoch")
    val timeEpoch: Long,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("is_day")
    val day: Long,
    @Embedded
    val condition: Condition,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    val humidity: Long,
) : Serializable


data class CurrentWeatherWithForeCast(
    @Embedded
    val weatherEntity: WeatherEntity,
//    val forecastDayEntity: List<ForecastDayEntity>
)