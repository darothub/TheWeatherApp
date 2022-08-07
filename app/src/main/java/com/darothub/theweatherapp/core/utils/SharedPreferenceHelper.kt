package com.darothub.theweatherapp.com.darothub.theweatherapp.core.utils

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.google.gson.Gson
const val ENCRYPTED = "encrypted"
class SharedPreferenceHelper(private val sharedPref: EncryptedSharedPreferences) {
    private val editor = sharedPref.edit()
    fun save(encryptedData:String) {
        editor.apply {
            putString(ENCRYPTED, encryptedData)
            apply()
        }
    }

    fun retrieve(): String? {
       return sharedPref.getString(ENCRYPTED, "")
    }
}