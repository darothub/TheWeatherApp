package com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model

import androidx.room.Embedded

data class Forecastday(
    val date: String,
    val dateEpoch: Long,
    @Embedded
    val astro: Astro,
    @Embedded
    val hour: List<Hour>
)