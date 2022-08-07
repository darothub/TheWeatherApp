package com.darothub.theweatherapp.com.darothub.theweatherapp.core.utils

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object CreateEncryptedSharedPref {

  fun create(context: Context): EncryptedSharedPreferences{
      val spec = KeyGenParameterSpec.Builder(
          MasterKey.DEFAULT_MASTER_KEY_ALIAS,
          KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
      )
          .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
          .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
          .setKeySize(3)
          .build()

      val masterKey = MasterKey.Builder(context)
          .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
          .build()

      return EncryptedSharedPreferences.create(
          context,
          "cypher",
          masterKey,
          EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
          EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
      ) as EncryptedSharedPreferences
  }
}