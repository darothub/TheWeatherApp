package com.darothub.theweatherapp.com.darothub.theweatherapp.domain

import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.HourEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.WeatherResponse

class HourMapper : Mapper<WeatherResponse, List<HourEntity>?> {
    override fun toEntity(value: WeatherResponse): List<HourEntity>? {
        return value.forecast?.forecastday?.flatMap { f ->
            f.hour.map { h->
                HourEntity(
                    timeEpoch = h.timeEpoch,
                    tempC = h.tempC,
                    condition = h.condition,
                    windMph = h.windMph,
                    pressureIn = h.pressureIn,
                    humidity = h.humidity,
                    hourId = f.dateEpoch
                )
            }
        }
    }
}