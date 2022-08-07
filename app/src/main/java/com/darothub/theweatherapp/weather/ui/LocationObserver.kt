package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.ui

import android.annotation.SuppressLint
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority

class LocationObserver(private val fusedLocationProviderClient: FusedLocationProviderClient): DefaultLifecycleObserver {
    lateinit var callback: LocationCallback
    @SuppressLint("MissingPermission")
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        callback = object : LocationCallback(){}
        val locationRequest = LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 2000
            priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback(){},
            Looper.getMainLooper()
        )
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        fusedLocationProviderClient.removeLocationUpdates(callback)
    }
}