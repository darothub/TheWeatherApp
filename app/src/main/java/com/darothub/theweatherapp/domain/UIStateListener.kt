package com.darothub.theweatherapp.com.darothub.theweatherapp.domain

import android.location.Location

interface UIStateListener {
    fun <T> onSuccess(data: T)
    fun <T>onWeatherResponse(data: T)
    fun onError(error: String?)
    fun loading()
    fun onLocationReady(location: Location)
    fun onRegisterBiometric()
}