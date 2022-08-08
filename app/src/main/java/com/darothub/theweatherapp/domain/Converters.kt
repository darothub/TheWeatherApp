package com.darothub.theweatherapp.com.darothub.theweatherapp.domain

import androidx.room.TypeConverter
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Forecast
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Forecastday
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    val gson = Gson()
    @TypeConverter
    fun fromListForecastDay(forecastdays: List<Forecastday>): String {
        return gson.toJson(forecastdays)
    }
    @TypeConverter
    fun toListForecastDay(json: String): List<Forecastday> {
        val forecastdays: Type = object :
            TypeToken<List<Forecastday>?>() {}.type
        return gson.fromJson(json, forecastdays)
    }

    @TypeConverter
    fun fromForecast(forecast: Forecast): String {
        return gson.toJson(forecast)
    }
    @TypeConverter
    fun toForecast(json: String): Forecast {
        val forecast: Type = object :
            TypeToken<Forecast?>() {}.type
        return gson.fromJson(json, forecast)
    }
}