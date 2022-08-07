package com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities

import androidx.room.*
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Astro

@Entity
data class ForecastDayEntity(
    val forecastId: Long = 0,
    @PrimaryKey
    val date: String,
    @ColumnInfo(name = "location_name")
    val locationName: String,
    val dateEpoch: Long,
    @Embedded
    val astro: Astro,
)


@Entity(primaryKeys = ["dateEpoch", "hourId"])
data class ForecastDayAndHourCrossRef(
    val dateEpoch: Long,
    val hourId: Long
)

data class ForecastDayWithHours(
    @Embedded val forcastDay: ForecastDayEntity,
    @Relation(
        parentColumn = "dateEpoch",
        entityColumn = "hourId",
        associateBy = Junction(ForecastDayAndHourCrossRef::class)
    )
    val hours: List<HourEntity>
)
