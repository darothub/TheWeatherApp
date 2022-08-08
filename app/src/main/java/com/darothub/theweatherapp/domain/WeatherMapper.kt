package com.darothub.theweatherapp.com.darothub.theweatherapp.domain

import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.WeatherEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.WeatherResponse

class WeatherMapper: Mapper<WeatherResponse, WeatherEntity?> {
    override fun toEntity(value: WeatherResponse): WeatherEntity? {
        return null
    }
}