package com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.CurrentWeatherEntity


data class WeatherResponse (
    val current: CurrentWeatherEntity,
    val location: Location,
    val forecast: Forecast?
)
