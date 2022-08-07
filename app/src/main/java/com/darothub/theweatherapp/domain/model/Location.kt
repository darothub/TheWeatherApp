package com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model

import androidx.room.PrimaryKey

data class Location (
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val localtimeEpoch: Long,
    val localtime: String
)