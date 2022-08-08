package com.darothub.theweatherapp.com.darothub.theweatherapp.main

import android.app.Application
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.api.ApiService
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.api.baseUrl
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.WeatherDatabase
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.CurrentDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.ForecastDayDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.HourDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.WeatherDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.ForecastDayMapper
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.WeatherMapper
import com.darothub.theweatherapp.com.darothub.theweatherapp.weather.repository.WeatherDataSource
import com.darothub.theweatherapp.com.darothub.theweatherapp.weather.repository.WeatherRepositoryImpl
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        currentDao = WeatherDatabase(this).weatherDao()
    }
    companion object {
        lateinit var forecastDayDao: ForecastDayDao
        lateinit var hourDao: HourDao
        lateinit var currentDao: WeatherDao
        private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
        private fun provideOkHttpClient(): okhttp3.OkHttpClient {
            return okhttp3.OkHttpClient.Builder()
                .addInterceptor(provideLoggingInterceptor())
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .retryOnConnectionFailure(false)
                .build()
        }
        fun createWeatherRepository() = WeatherRepositoryImpl(
            getRetrofit(),
            createDataSource()
        )

        private fun createDataSource() = WeatherDataSource(currentDao)

        private fun getRetrofit(): ApiService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(provideOkHttpClient())
                .build()
                .create(ApiService::class.java)
        }
    }
}