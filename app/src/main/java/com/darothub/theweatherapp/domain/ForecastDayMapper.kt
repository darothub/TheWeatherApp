package com.darothub.theweatherapp.com.darothub.theweatherapp.domain

import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.ForecastDayEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.WeatherResponse

class ForecastDayMapper : Mapper<WeatherResponse, List<ForecastDayEntity>> {
    override fun toEntity(value: WeatherResponse): List<ForecastDayEntity> {
       return value.forecast?.forecastday!!.map {
            ForecastDayEntity(
                date = it.date,
                dateEpoch = it.dateEpoch,
                locationName = value.location.name,
                astro = it.astro
            )
        }
    }
}
