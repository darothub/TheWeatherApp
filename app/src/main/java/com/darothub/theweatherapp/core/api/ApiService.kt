package com.darothub.theweatherapp.com.darothub.theweatherapp.core.api

import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.WeatherEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val baseUrl = "https://api.weatherapi.com/v1/"
interface ApiService {
    @GET("forecast.json")
    suspend fun getCurrentWeather(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: Int,
    ): WeatherEntity

    @GET("forecast.json")
    suspend fun getWeatherForeCast(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: Int,
    ): WeatherResponse
}