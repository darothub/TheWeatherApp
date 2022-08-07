package com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities

import androidx.room.Embedded
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Condition
import com.google.gson.annotations.SerializedName


data class CurrentWeatherEntity(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Long,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("temp_c")
    val tempC: Double,
    @Embedded
    val condition: Condition,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    val humidity: Long,
)




