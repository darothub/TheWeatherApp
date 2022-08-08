package com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

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