package com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Condition
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class HourEntity(
    @PrimaryKey(autoGenerate = true)
    val hourId: Long = 0,
    @SerializedName("time_epoch")
    val timeEpoch: Long,
    @SerializedName("temp_c")
    val tempC: Double,
    @Embedded
    val condition: Condition,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    val humidity: Long,
) : Serializable
