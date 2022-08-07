package com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model


data class Forecast (
    val id: Long = 0,
    val forecastday: List<ForecastDay>
)
