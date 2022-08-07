package com.darothub.theweatherapp.com.darothub.theweatherapp.domain

interface UIStateListener {
    fun <T> onSuccess(data: T)
    fun onError(error: String?)
    fun loading()
}