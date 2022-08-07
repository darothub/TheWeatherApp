package com.darothub.theweatherapp
object Keys {
    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
}