package com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model

import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.HourEntity

data class ForecastDay(
    val date: String,
    val dateEpoch: Long,
    val astro: Astro,
    val hour: List<HourEntity>
)
