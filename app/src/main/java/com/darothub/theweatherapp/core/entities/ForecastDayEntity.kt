package com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities

import androidx.room.*
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Astro

@Entity
data class ForecastDayEntity(
    @PrimaryKey(autoGenerate = true)
    val forecastId: Long = 0,
    val date: String,
    @ColumnInfo(name = "location_name")
    val locationName: String,
    val dateEpoch: Long,
    @Embedded
    val astro: Astro,
)

@Entity(primaryKeys = ["forecastId", "hourId"])
data class ForecastDayAndHourCrossRef(
    val forecastId: Long,
    val hourId: Long
)

data class ForecastDayWithHours(
    @Embedded val playlist: ForecastDayEntity,
    @Relation(
        parentColumn = "forecastId",
        entityColumn = "hourId",
        associateBy = Junction(ForecastDayAndHourCrossRef::class)
    )
    val hours: List<HourEntity>
)
