package com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.HourEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForecastDay(
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Long,
    val astro: Astro,
    val hour: List<HourEntity>
)

data class ForecastDayResponse(
    val forecastId: Long = 0,
    val date: String,
    val dateEpoch: Long,
    @Embedded
    val astro: Astro,
    val hourId: Long = 0,
    val timeEpoch: Long,
    val tempC: Double,
    @Embedded
    val condition: Condition,
    val windMph: Double,
    val pressureIn: Double,
    val humidity: Long,
)

//@Entity
//data class HourEntity(
//    @PrimaryKey(autoGenerate = true)
//    val hourId: Long = 0,
//    @SerializedName("time_epoch")
//    val timeEpoch: Long,
//    @SerializedName("temp_c")
//    val tempC: Double,
//    @Embedded
//    val condition: Condition,
//    @SerializedName("wind_mph")
//    val windMph: Double,
//    @SerializedName("pressure_in")
//    val pressureIn: Double,
//    val humidity: Long,
//) : Serializable

